package com.andreivanceadev.data.datamodel

import com.andreivanceadev.data.entity.RewardDto

data class RewardDM(
    val title: String,
    val imagePath: String
)

internal fun RewardDM.toDto(objectiveId: Long) = RewardDto(
    title = this.title,
    imagePath = this.imagePath,
    objectiveId = objectiveId
)

internal fun RewardDto.toDM() = RewardDM(
    title = this.title,
    imagePath = this.imagePath
)
