package com.andreivanceadev.objectives.di

import com.andreivanceadev.data.repository.ObjectivesRepository
import com.andreivanceadev.objectives.viewmodel.AddObjectiveViewModel
import com.andreivanceadev.objectives.viewmodel.AddObjectiveViewState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ObjectivesModule {

    @Provides
    fun provideObjectivesViewModel(objectivesRepository: ObjectivesRepository) = AddObjectiveViewModel(
        objectivesRepository,
        AddObjectiveViewState(),
    )
}
