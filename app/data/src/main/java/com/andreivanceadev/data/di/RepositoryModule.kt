package com.andreivanceadev.data.di

import com.andreivanceadev.data.dao.ObjectiveRewardsDao
import com.andreivanceadev.data.repository.ObjectivesRepository
import com.andreivanceadev.data.repository.ObjectivesRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    internal fun provideObjectivesRepository(
        objectiveRewardsDao: ObjectiveRewardsDao
    ): ObjectivesRepository =
        ObjectivesRepositoryImpl(objectiveRewardsDao, Dispatchers.IO)
}