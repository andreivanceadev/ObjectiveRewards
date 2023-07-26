package com.andreivanceadev.data.repository

import com.andreivanceadev.data.dao.ObjectiveRewardsDao
import com.andreivanceadev.data.datamodel.ObjectiveDM
import com.andreivanceadev.data.datamodel.toDM
import com.andreivanceadev.data.datamodel.toDto
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class ObjectivesRepositoryImpl @Inject constructor(
    private val objectiveRewardsDao: ObjectiveRewardsDao,
    private val dispatcher: CoroutineDispatcher
) : ObjectivesRepository {

    override suspend fun getObjectives() =
        withContext(dispatcher) {
            objectiveRewardsDao.getObjectives().map { objectiveAndReward ->
                objectiveAndReward.reward.imagePath
                objectiveAndReward.toDM()
            }
        }

    override suspend fun addObjective(objective: ObjectiveDM) {
        withContext(dispatcher) {
            val objectiveId = objectiveRewardsDao.insertObjective(objective.toDto())
            objectiveRewardsDao.insertReward(reward = objective.reward.toDto(objectiveId))
        }
    }

}