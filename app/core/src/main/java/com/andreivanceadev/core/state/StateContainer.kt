package com.andreivanceadev.core.state

import app.cash.turbine.ReceiveTurbine
import app.cash.turbine.testIn
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

private const val DEFAULT_METHOD_NAME = "Unknown"
private typealias OnIntentEvent<STATE, EFFECT> = (IntentEvent<STATE, EFFECT>) -> Unit

interface StateContainerHost<STATE, EFFECT> {

    val container: StateContainer<STATE, EFFECT>
}

interface StateContainer<STATE, EFFECT> {

    val stateFlow: StateFlow<STATE>

    val effectFlow: Flow<EFFECT>

    fun intent(name: String, block: suspend IntentScope<STATE, EFFECT>.() -> Unit)

    interface StateScope<STATE> {
        val state: STATE
    }

    interface EffectScope<EFFECT> {
        fun postEffect(value: EFFECT)
    }

    interface ReduceScope<STATE, EFFECT> : StateScope<STATE>, EffectScope<EFFECT>

    interface IntentScope<STATE, EFFECT> : StateScope<STATE>, EffectScope<EFFECT>, IntentCoroutineScope {
        fun reduce(block: ReduceScope<STATE, EFFECT>.() -> STATE): Boolean
    }

    interface IntentCoroutineScope : CoroutineScope {
        val job: Job
    }

    class Builder<STATE, EFFECT>(private val initialState: STATE) {
        private var onIntentEvent = setOf<OnIntentEvent<STATE, EFFECT>>()
        private var scope: CoroutineScope? = null

        fun scope(scope: CoroutineScope) = apply {
            this.scope = scope
        }

        fun onEvent(block: OnIntentEvent<STATE, EFFECT>) = apply {
            this.onIntentEvent = this.onIntentEvent + block
        }

        private fun onEvent(event: IntentEvent<STATE, EFFECT>) {
            onIntentEvent.forEach { block -> block(event) }
        }

        fun build(): StateContainer<STATE, EFFECT> {
            val scope = this.scope ?: CoroutineScope(Dispatchers.Main)
            return StateContainerImpl(initialState, scope, ::onEvent)
        }
    }
}

data class IntentSession(val name: String, val id: Long)

sealed class IntentEvent<STATE, EFFECT> {

    abstract val session: IntentSession

    data class Start<STATE, EFFECT>(
        override val session: IntentSession
    ) : IntentEvent<STATE, EFFECT>()

    data class Mutation<STATE, EFFECT>(
        val previous: STATE,
        val next: STATE,
        override val session: IntentSession
    ) : IntentEvent<STATE, EFFECT>()

    data class Effect<STATE, EFFECT>(
        val effect: EFFECT,
        override val session: IntentSession
    ) : IntentEvent<STATE, EFFECT>()

    data class End<STATE, EFFECT>(
        val isCancelled: Boolean,
        override val session: IntentSession
    ) : IntentEvent<STATE, EFFECT>()
}

private class StateContainerImpl<STATE, EFFECT>(
    initialState: STATE,
    private val scope: CoroutineScope,
    private val onIntentEvent: OnIntentEvent<STATE, EFFECT>
) : StateContainer<STATE, EFFECT> {

    private val intentSessionCount = mutableMapOf<String, Long>()

    private val lock = Any()

    private val mutableStateFlow = MutableStateFlow(initialState)

    private val sideEffectChannel = Channel<EFFECT>(capacity = Channel.UNLIMITED)

    override val stateFlow = SynchronizedStateFlow(lock, mutableStateFlow.asStateFlow())

    override val effectFlow: Flow<EFFECT> = sideEffectChannel.receiveAsFlow()

    override fun intent(name: String, block: suspend StateContainer.IntentScope<STATE, EFFECT>.() -> Unit) {
        val id = synchronized(lock) { intentSessionCount.getAndIncrement(name) }
        val session = IntentSession(name, id)
        val job = Job()
        scope.launch(context = job) {
            try {
                onIntentEvent(IntentEvent.Start(session))
                TransactionScopeImpl(job, this, session).block()
            } finally {
                onIntentEvent(IntentEvent.End(job.isCancelled, session))
            }
        }
    }

    private inner class TransactionScopeImpl(
        override val job: Job,
        private val scope: CoroutineScope,
        private val session: IntentSession
    ) : StateContainer.IntentScope<STATE, EFFECT>, StateContainer.IntentCoroutineScope, CoroutineScope by scope {

        override val state: STATE
            get() = stateFlow.value

        override fun postEffect(value: EFFECT) {
            /**
             * trySend on Channel with capacity = Channel.UNLIMITED is always successful
             */
            sideEffectChannel.trySend(value)
            onIntentEvent(IntentEvent.Effect(effect = value, session = session))
        }

        override fun reduce(
            block: StateContainer.ReduceScope<STATE, EFFECT>.() -> STATE
        ): Boolean {
            val (mutation, effects) = synchronized(lock) {
                val effects = mutableListOf<EFFECT>()
                val current = mutableStateFlow.value
                val reduceScope =
                    object : StateContainer.ReduceScope<STATE, EFFECT>, StateContainer.EffectScope<EFFECT> {
                        override val state: STATE = current
                        override fun postEffect(value: EFFECT) {
                            effects.add(value)
                        }
                    }
                val next = block(reduceScope)
                if (current != next) {
                    mutableStateFlow.value = next
                    IntentEvent.Mutation<STATE, EFFECT>(previous = current, next = next, session = session) to effects
                } else {
                    null to effects
                }
            }

            if (mutation != null) {
                onIntentEvent(mutation)
            }

            effects.forEach(
                /**
                 * Effects should be notified only after the mutation and outside state lock,
                 * otherwise observers might try to react to a effect while the state is not yet updated.
                 */
                ::postEffect
            )

            return mutation != null
        }
    }
}

private class SynchronizedStateFlow<STATE>(
    private val lock: Any,
    private val delegate: StateFlow<STATE>
) : StateFlow<STATE> by delegate {

    override val value: STATE
        get() = synchronized(lock) { delegate.value }
}

fun <STATE, EFFECT> StateContainer.Builder<STATE, EFFECT>.onMutationEvent(
    block: (IntentEvent.Mutation<STATE, EFFECT>) -> Unit
) = apply {
    onEvent { event ->
        if (event is IntentEvent.Mutation) {
            block(event)
        }
    }
}

fun <STATE, EFFECT> StateContainer.Builder<STATE, EFFECT>.onEffectEvent(
    block: (IntentEvent.Effect<STATE, EFFECT>) -> Unit
) = apply {
    onEvent { event ->
        if (event is IntentEvent.Effect) {
            block(event)
        }
    }
}

fun <STATE, EFFECT> StateContainer.Builder<STATE, EFFECT>.onSimpleEvent(
    block: (IntentEvent<STATE, EFFECT>) -> Unit
) = apply {
    onEvent { event ->
        if (event is IntentEvent.Effect || event is IntentEvent.Mutation) {
            block(event)
        }
    }
}

fun <STATE, EFFECT> StateContainer<STATE, EFFECT>.reduce(
    block: StateContainer.ReduceScope<STATE, EFFECT>.() -> STATE
) {
    val name = Throwable().stackTrace.getOrNull(1)?.methodName ?: DEFAULT_METHOD_NAME
    reduce(name = name, block = block)
}

fun <STATE, EFFECT> StateContainer<STATE, EFFECT>.intent(
    block: suspend StateContainer.IntentScope<STATE, EFFECT>.() -> Unit
) {
    val name = Throwable().stackTrace.getOrNull(1)?.methodName ?: DEFAULT_METHOD_NAME
    intent(name = name, block = block)
}

fun <STATE, EFFECT> StateContainer<STATE, EFFECT>.reduce(
    name: String,
    block: StateContainer.ReduceScope<STATE, EFFECT>.() -> STATE
) {
    intent(name) {
        reduce {
            block()
        }
    }
}

fun <STATE, EFFECT> StateContainer<STATE, EFFECT>.postEffect(
    effect: EFFECT
) {
    val name = Throwable().stackTrace.getOrNull(1)?.methodName ?: DEFAULT_METHOD_NAME
    postEffect(name = name, effect = effect)
}

fun <STATE, EFFECT> StateContainer<STATE, EFFECT>.postEffect(
    name: String,
    effect: EFFECT
) {
    intent(name) {
        postEffect(effect)
    }
}

@StateContainerDsl
fun <STATE, EFFECT> StateContainerHost<STATE, EFFECT>.intent(
    block: suspend StateContainer.IntentScope<STATE, EFFECT>.() -> Unit
) {
    val name = Throwable().stackTrace.getOrNull(1)?.methodName ?: DEFAULT_METHOD_NAME
    intent(name = name, block = block)
}

@StateContainerDsl
fun <STATE, EFFECT> StateContainerHost<STATE, EFFECT>.intent(
    name: String,
    block: suspend StateContainer.IntentScope<STATE, EFFECT>.() -> Unit
) = container.intent(name = name, block = block)

@StateContainerDsl
fun <STATE, EFFECT> StateContainerHost<STATE, EFFECT>.reduce(
    block: StateContainer.ReduceScope<STATE, EFFECT>.() -> STATE
) {
    val name = Throwable().stackTrace.getOrNull(1)?.methodName ?: DEFAULT_METHOD_NAME
    reduce(name = name, block = block)
}

@StateContainerDsl
fun <STATE, EFFECT> StateContainerHost<STATE, EFFECT>.reduce(
    name: String,
    block: StateContainer.ReduceScope<STATE, EFFECT>.() -> STATE
) = container.reduce(name = name, block = block)

val <STATE, EFFECT> StateContainerHost<STATE, EFFECT>.stateFlow: StateFlow<STATE>
    get() = container.stateFlow

val <STATE, EFFECT> StateContainerHost<STATE, EFFECT>.effectFlow: Flow<EFFECT>
    get() = container.effectFlow

private fun MutableMap<String, Long>.getAndIncrement(key: String): Long {
    val id = this[key] ?: 0L
    this[key] = id + 1
    return id
}

fun <STATE, EFFECT> IntentEvent<STATE, EFFECT>.toPrettyString(): String = when (this) {
    is IntentEvent.Start -> "${session.toPrettyString()} -->"
    is IntentEvent.End -> if (isCancelled) {
        "${session.toPrettyString(false)}:Cancelled] <--"
    } else {
        "${session.toPrettyString()} <--"
    }

    is IntentEvent.Effect -> "${session.toPrettyString(false)}:E] $effect"
    is IntentEvent.Mutation -> "${session.toPrettyString(false)}:M] $previous -> $next"
}

private fun IntentSession.toPrettyString(close: Boolean = true): String {
    return "$name [$id${if (close) "]" else ""}"
}

suspend fun <STATE, EFFECT, HOST : StateContainerHost<STATE, EFFECT>> HOST.test(
    ignoreInitial: Boolean = true,
    block: suspend HOST.(state: ReceiveTurbine<STATE>, effect: ReceiveTurbine<EFFECT>) -> Unit
) = coroutineScope {
    val state = stateFlow.testIn(this)
    val effect = effectFlow.testIn(this)

    try {
        if (ignoreInitial) {
            // First item is the initialState
            state.awaitItem()
        }

        block(state, effect)
    } finally {
        state.cancelAndIgnoreRemainingEvents()
        effect.cancelAndIgnoreRemainingEvents()
    }
}

@DslMarker
annotation class StateContainerDsl
