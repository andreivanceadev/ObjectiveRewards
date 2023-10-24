package com.andreivanceadev.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.andreivanceadev.data.dto.RewardDto
import com.andreivanceadev.data.entity.RewardEntity

@Dao
internal abstract class RewardsDao {

    @Transaction
    @Query("SELECT * FROM rewards")
    abstract suspend fun getRewards(): List<RewardDto>

    @Transaction
    @Query("SELECT * FROM rewards WHERE reward_id = :rewardId LIMIT 1")
    abstract suspend fun getRewardById(rewardId: Long): RewardDto

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertReward(reward: RewardEntity): Long

    @Update
    abstract suspend fun updateReward(reward: RewardEntity)

    @Delete
    abstract suspend fun deleteReward(reward: RewardEntity)


}
