package com.andreivanceadev.data.entity

import androidx.room.Embedded
import androidx.room.Relation

internal data class ObjectiveAndRewardDto(
    @Embedded val objective: ObjectiveDto,
    @Relation(
        parentColumn = "id",
        entityColumn = "objective_id"
    )
    val reward: RewardDto
)