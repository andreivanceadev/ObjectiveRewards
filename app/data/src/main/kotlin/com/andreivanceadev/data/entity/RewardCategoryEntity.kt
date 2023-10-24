package com.andreivanceadev.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "reward_category",
    indices = [Index(value = ["display_name"], unique = true)],
)
data class RewardCategoryEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "category_id") val id: Long = 0,
    @ColumnInfo(name = "display_name") val displayName: String,
)
