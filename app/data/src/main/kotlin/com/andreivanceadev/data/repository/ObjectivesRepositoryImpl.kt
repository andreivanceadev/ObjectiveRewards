package com.andreivanceadev.data.repository

import com.andreivanceadev.data.dao.ObjectivesDao
import com.andreivanceadev.data.dao.RewardsDao
import com.andreivanceadev.data.datamodel.ObjectiveDM
import com.andreivanceadev.data.datamodel.toDM
import com.andreivanceadev.data.datamodel.toEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class ObjectivesRepositoryImpl @Inject constructor(
    private val objectivesDao: ObjectivesDao,
    private val rewardsDao: RewardsDao,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : ObjectivesRepository {

    override suspend fun getObjectives() =
        withContext(dispatcher) {
            objectivesDao.getObjectives().map {
                ObjectiveDM(
                    id = it.id,
                    title = it.title,
                    desc = it.description,
                    reward = rewardsDao.getRewardById(it.rewardId).toDM(),
                )
            }
        }

    override suspend fun addObjective(objective: ObjectiveDM) {
        withContext(dispatcher) {
            val rewardId = rewardsDao.insertReward(objective.reward.toEntity())
            objectivesDao.insertObjective(objective.toEntity(rewardId))
        }
    }

}
