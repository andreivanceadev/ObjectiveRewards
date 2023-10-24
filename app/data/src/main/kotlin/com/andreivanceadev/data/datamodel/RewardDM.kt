package com.andreivanceadev.data.datamodel

import com.andreivanceadev.data.dto.RewardDto
import com.andreivanceadev.data.entity.RewardEntity

data class RewardDM(
    val id: Long,
    val title: String,
    val imagePath: String,
    val category: RewardCategoryDM,
)

fun RewardDto.toDM() = RewardDM(
    id = this.reward.id,
    title = this.reward.title,
    imagePath = this.reward.imagePath,
    category = this.category.toDM(),
)

fun RewardDM.toEntity() = RewardEntity(
    id = this.id,
    title = this.title,
    imagePath = this.imagePath,
    categoryId = this.category.id,
)
