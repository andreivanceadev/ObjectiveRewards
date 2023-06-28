package com.andreivanceadev.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rewards")
internal data class RewardDto(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "reward_id") val rewardId: Long = 0,
    @ColumnInfo(name = "reward_title") val title: String,
    @ColumnInfo(name = "reward_image") val imagePath: String,
    @ColumnInfo(name = "objective_id") val objectiveId: Long
)