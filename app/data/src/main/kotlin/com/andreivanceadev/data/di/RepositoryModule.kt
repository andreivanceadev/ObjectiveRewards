package com.andreivanceadev.data.di

import com.andreivanceadev.data.dao.ObjectivesDao
import com.andreivanceadev.data.dao.RewardsCategoryDao
import com.andreivanceadev.data.dao.RewardsDao
import com.andreivanceadev.data.repository.ObjectivesRepository
import com.andreivanceadev.data.repository.ObjectivesRepositoryImpl
import com.andreivanceadev.data.repository.RewardsRepository
import com.andreivanceadev.data.repository.RewardsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    internal fun provideObjectivesRepository(
        objectivesDao: ObjectivesDao,
        rewardsDao: RewardsDao,
    ): ObjectivesRepository =
        ObjectivesRepositoryImpl(objectivesDao, rewardsDao)

    @Provides
    internal fun provideRewardsRepository(
        rewardsDao: RewardsDao,
        rewardsCategoryDao: RewardsCategoryDao,
    ): RewardsRepository =
        RewardsRepositoryImpl(rewardsDao, rewardsCategoryDao)
}
