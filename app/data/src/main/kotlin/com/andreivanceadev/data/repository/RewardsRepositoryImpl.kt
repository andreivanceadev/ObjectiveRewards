package com.andreivanceadev.data.repository

import com.andreivanceadev.data.dao.RewardsCategoryDao
import com.andreivanceadev.data.dao.RewardsDao
import com.andreivanceadev.data.datamodel.RewardCategoryDM
import com.andreivanceadev.data.datamodel.RewardDM
import com.andreivanceadev.data.datamodel.toDM
import com.andreivanceadev.data.datamodel.toEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class RewardsRepositoryImpl @Inject constructor(
    private val rewardsDao: RewardsDao,
    private val rewardsCategoryDao: RewardsCategoryDao,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : RewardsRepository {
    override suspend fun getRewards() = withContext(dispatcher) {
        rewardsDao.getRewards().map { it.toDM() }
    }

    override suspend fun saveReward(rewardDM: RewardDM) {
        withContext(dispatcher) {
            rewardsDao.insertReward(rewardDM.toEntity())
        }
    }

    override suspend fun getAllCategories() = withContext(dispatcher) {
        rewardsCategoryDao.getCategories().map { it.toDM() }
    }

    override suspend fun saveCategory(category: RewardCategoryDM) {
        withContext(dispatcher) {
            rewardsCategoryDao.insertCategory(category.toEntity())
        }
    }
}
