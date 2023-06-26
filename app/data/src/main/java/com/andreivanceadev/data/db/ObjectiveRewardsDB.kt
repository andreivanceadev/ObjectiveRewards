package com.andreivanceadev.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.andreivanceadev.data.dao.ObjectiveRewardsDao
import com.andreivanceadev.data.entity.ObjectiveDto
import com.andreivanceadev.data.entity.RewardDto

@Database(entities = [ObjectiveDto::class, RewardDto::class], version = 1)
abstract class ObjectiveRewardsDB : RoomDatabase() {

    abstract fun objectiveRewardsDao(): ObjectiveRewardsDao

    companion object {

        private const val DATABASE_NAME = "objectiverewards-db"

        // For Singleton instantiation
        @Volatile
        private var instance: ObjectiveRewardsDB? = null

        fun getInstance(context: Context): ObjectiveRewardsDB {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): ObjectiveRewardsDB {
            return Room.databaseBuilder(context, ObjectiveRewardsDB::class.java, DATABASE_NAME)
                .build()
        }
    }
}