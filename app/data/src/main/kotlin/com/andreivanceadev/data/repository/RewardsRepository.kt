package com.andreivanceadev.data.repository

import com.andreivanceadev.data.datamodel.RewardCategoryDM
import com.andreivanceadev.data.datamodel.RewardDM

interface RewardsRepository {

    suspend fun getRewards(): List<RewardDM>

    suspend fun saveReward(rewardDM: RewardDM)

    suspend fun getAllCategories(): List<RewardCategoryDM>

    suspend fun saveCategory(category: RewardCategoryDM)
}
