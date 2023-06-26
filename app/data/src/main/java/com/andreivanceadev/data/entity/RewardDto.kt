package com.andreivanceadev.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rewards")
data class RewardDto(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "reward_id") val rewardId: Int,
    @ColumnInfo(name = "reward_title") val title: String,
    @ColumnInfo(name = "reward_image") val imageUrl: String,
)