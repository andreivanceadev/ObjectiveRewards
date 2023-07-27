package com.andreivanceadev.dashboard.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andreivanceadev.core.state.StateContainer
import com.andreivanceadev.core.state.StateContainerHost
import com.andreivanceadev.core.state.intent
import com.andreivanceadev.core.state.onSimpleEvent
import com.andreivanceadev.core.state.toPrettyString
import com.andreivanceadev.data.repository.ObjectivesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val objectivesRepository: ObjectivesRepository,
    initialState: DashboardViewState = DashboardViewState.NoContent,
) : ViewModel(), StateContainerHost<DashboardViewState, DashboardSideEffect> {

    override val container = StateContainer
        .Builder<DashboardViewState, DashboardSideEffect>(initialState)
        .scope(viewModelScope)
        .onSimpleEvent { Timber.d(it.toPrettyString()) }
        .build()

    fun onStart() = intent {
        val objectives = objectivesRepository.getObjectives().map { it.toUI() }
        if (objectives.isEmpty()) {
            reduce { DashboardViewState.NoContent }
        } else {
            reduce { DashboardViewState.Content(objectives) }
        }
    }
}
