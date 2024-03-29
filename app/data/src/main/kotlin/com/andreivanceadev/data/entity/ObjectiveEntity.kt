package com.andreivanceadev.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "objectives",
)
data class ObjectiveEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo("objective_id")  val id: Long = 0,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "reward") val rewardId: Long,
)
