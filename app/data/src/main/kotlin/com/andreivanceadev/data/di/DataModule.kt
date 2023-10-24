package com.andreivanceadev.data.di

import android.content.Context
import com.andreivanceadev.data.dao.ObjectivesDao
import com.andreivanceadev.data.dao.RewardsCategoryDao
import com.andreivanceadev.data.dao.RewardsDao
import com.andreivanceadev.data.db.ObjectiveRewardsDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataModule {

    @Singleton
    @Provides
    internal fun provideDatabase(@ApplicationContext context: Context): ObjectiveRewardsDB =
        ObjectiveRewardsDB.getInstance(context)

    @Provides
    internal fun provideObjectivesDao(db: ObjectiveRewardsDB): ObjectivesDao = db.objectivesDao()

    @Provides
    internal fun provideRewardsDao(db: ObjectiveRewardsDB): RewardsDao = db.rewardsDao()

    @Provides
    internal fun provideRewardsCategoryDao(db: ObjectiveRewardsDB): RewardsCategoryDao = db.rewardsCategoryDao()

}
