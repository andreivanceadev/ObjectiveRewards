package com.andreivanceadev.data.datamodel

import com.andreivanceadev.data.entity.RewardCategoryEntity

data class RewardCategoryDM(
    val id: Long,
    val displayName: String,
)

fun RewardCategoryDM.toEntity() = RewardCategoryEntity(
    id = this.id,
    displayName = this.displayName,
)

fun RewardCategoryEntity.toDM() = RewardCategoryDM(
    id = this.id,
    displayName = this.displayName,
)
