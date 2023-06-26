package com.andreivanceadev.data.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "objectives"
)
data class ObjectiveDto(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val objectiveId: Int = 0,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "description") val description: String,
    @Embedded val reward: RewardDto
)