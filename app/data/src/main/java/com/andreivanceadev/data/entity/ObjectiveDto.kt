package com.andreivanceadev.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "objectives"
)
internal data class ObjectiveDto(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val objectiveId: Long = 0,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "description") val description: String,
)