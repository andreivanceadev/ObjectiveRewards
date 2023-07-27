package com.andreivanceadev.dashboard.di

import com.andreivanceadev.dashboard.viewmodel.DashboardViewModel
import com.andreivanceadev.data.repository.ObjectivesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DashboardModule {

    @Provides
    fun provideDashboardViewModel(objectivesRepository: ObjectivesRepository): DashboardViewModel =
        DashboardViewModel(objectivesRepository)
}
