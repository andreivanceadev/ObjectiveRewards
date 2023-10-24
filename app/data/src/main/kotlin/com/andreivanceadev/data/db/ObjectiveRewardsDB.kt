package com.andreivanceadev.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.andreivanceadev.data.dao.ObjectivesDao
import com.andreivanceadev.data.dao.RewardsCategoryDao
import com.andreivanceadev.data.dao.RewardsDao
import com.andreivanceadev.data.entity.ObjectiveEntity
import com.andreivanceadev.data.entity.RewardCategoryEntity
import com.andreivanceadev.data.entity.RewardEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

@Database(
    entities = [
        ObjectiveEntity::class,
        RewardEntity::class,
        RewardCategoryEntity::class,
    ],
    version = 1,
)
internal abstract class ObjectiveRewardsDB : RoomDatabase() {

    abstract fun objectivesDao(): ObjectivesDao
    abstract fun rewardsDao(): RewardsDao
    abstract fun rewardsCategoryDao(): RewardsCategoryDao

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
                .addCallback(
                    object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            val dao = getInstance(context).rewardsCategoryDao()
                            runBlocking(Dispatchers.IO) {
                                RewardsCategoryPreSeed.entries.forEach {
                                    dao.insertCategory(it)
                                }
                            }
                        }
                    },
                )
                .build()
        }
    }
}
