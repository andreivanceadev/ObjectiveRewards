package com.andreivanceadev.data.dao

import android.database.sqlite.SQLiteConstraintException
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.andreivanceadev.data.entity.RewardCategoryEntity

@Dao
internal abstract class RewardsCategoryDao {

    @Transaction
    @Query("SELECT * FROM reward_category")
    abstract suspend fun getCategories(): List<RewardCategoryEntity>

    @Transaction
    @Query("SELECT * FROM reward_category WHERE display_name = :displayName LIMIT 1")
    abstract suspend fun getCategoryByName(displayName: String): RewardCategoryEntity


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @Throws(SQLiteConstraintException::class)
    abstract suspend fun insertCategory(category: RewardCategoryEntity): Long

    @Update
    @Throws(SQLiteConstraintException::class)
    abstract suspend fun updateCategory(category: RewardCategoryEntity)

    @Delete
    abstract suspend fun deleteCategory(category: RewardCategoryEntity)


}
