package com.andreivanceadev.objectives.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andreivanceadev.core.state.StateContainer
import com.andreivanceadev.core.state.StateContainerHost
import com.andreivanceadev.core.state.intent
import com.andreivanceadev.core.state.onSimpleEvent
import com.andreivanceadev.core.state.reduce
import com.andreivanceadev.core.state.toPrettyString
import com.andreivanceadev.data.repository.ObjectivesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AddObjectiveViewModel @Inject constructor(
    private val objectivesRepository: ObjectivesRepository,
    initialState: AddObjectiveViewState = AddObjectiveViewState(),
) : ViewModel(), StateContainerHost<AddObjectiveViewState, AddObjectiveSideEffect> {

    override val container = StateContainer
        .Builder<AddObjectiveViewState, AddObjectiveSideEffect>(initialState)
        .scope(viewModelScope)
        .onSimpleEvent { Timber.d(it.toPrettyString()) }
        .build()

    fun onSave() = intent {
        // TODO: Save the objective
        postEffect(AddObjectiveSideEffect.NavBack)
    }

    fun onImageChange(path: String) = reduce {
        state.copy(imagePath = path)
    }

    fun onTitleChange(title: String) = reduce {
        state.copy(title = title)
    }

    fun onDescriptionChange(desc: String) = reduce {
        state.copy(description = desc)
    }
}
