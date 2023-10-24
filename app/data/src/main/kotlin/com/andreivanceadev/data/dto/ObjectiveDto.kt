package com.andreivanceadev.data.dto

import com.andreivanceadev.data.entity.ObjectiveEntity

data class ObjectiveDto(
    val objective: ObjectiveEntity,
    val reward: RewardDto,
)
