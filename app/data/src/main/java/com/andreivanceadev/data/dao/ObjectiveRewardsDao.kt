package com.andreivanceadev.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.andreivanceadev.data.entity.ObjectiveDto
import kotlinx.coroutines.flow.Flow

@Dao
interface ObjectiveRewardsDao {

    @Transaction
    @Query("SELECT * FROM objectives")
    fun getObjectives(): Flow<List<ObjectiveDto>>

    @Insert
    suspend fun insertObjective(objective: ObjectiveDto): Long

    @Update
    suspend fun updateObjective(objective: ObjectiveDto)

    @Delete
    suspend fun deleteObjective(objective: ObjectiveDto)

}