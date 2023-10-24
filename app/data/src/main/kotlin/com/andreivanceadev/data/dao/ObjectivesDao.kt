package com.andreivanceadev.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.andreivanceadev.data.entity.ObjectiveEntity

@Dao
internal abstract class ObjectivesDao {

    @Transaction
    @Query("SELECT * FROM objectives")
    abstract suspend fun getObjectives(): List<ObjectiveEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertObjective(objective: ObjectiveEntity): Long

    @Update
    abstract suspend fun updateObjective(objective: ObjectiveEntity)

    @Delete
    abstract suspend fun deleteObjective(objective: ObjectiveEntity)

}
