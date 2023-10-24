package com.andreivanceadev.data.dto

import androidx.room.Embedded
import androidx.room.Relation
import com.andreivanceadev.data.entity.RewardCategoryEntity
import com.andreivanceadev.data.entity.RewardEntity

data class RewardDto(
    @Embedded val reward: RewardEntity,
    @Relation(
        parentColumn = "category",
        entityColumn = "category_id",
    )
    val category: RewardCategoryEntity,
)
