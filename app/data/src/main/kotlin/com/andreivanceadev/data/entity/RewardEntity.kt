package com.andreivanceadev.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rewards")
data class RewardEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo("reward_id") val id: Long = 0,
    @ColumnInfo(name = "reward_title") val title: String,
    @ColumnInfo(name = "reward_image") val imagePath: String,
    @ColumnInfo(name = "category") val categoryId: Long,
)
