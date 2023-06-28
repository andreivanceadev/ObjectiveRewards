package com.andreivanceadev.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.andreivanceadev.data.entity.ObjectiveAndRewardDto
import com.andreivanceadev.data.entity.ObjectiveDto
import com.andreivanceadev.data.entity.RewardDto
import kotlinx.coroutines.flow.Flow

@Dao
internal abstract class ObjectiveRewardsDao {

    @Transaction
    @Query("SELECT * FROM objectives")
    abstract suspend fun getObjectives(): List<ObjectiveAndRewardDto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertObjective(objective: ObjectiveDto): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertReward(reward: RewardDto): Long

    @Update
    abstract suspend fun updateObjective(objective: ObjectiveDto)

    @Delete
    abstract suspend fun deleteObjective(objective: ObjectiveDto)

}