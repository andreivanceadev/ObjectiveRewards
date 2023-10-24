package com.andreivanceadev.data.datamodel

import com.andreivanceadev.data.dto.ObjectiveDto
import com.andreivanceadev.data.entity.ObjectiveEntity

data class ObjectiveDM(
    val id: Long,
    val title: String,
    val desc: String,
    val reward: RewardDM,
)


internal fun ObjectiveDM.toEntity(rewardId: Long) = ObjectiveEntity(
    id = this.id,
    title = this.title,
    description = this.desc,
    rewardId = rewardId,
)

internal fun ObjectiveDto.toDM() = ObjectiveDM(
    id = this.objective.id,
    title = this.objective.title,
    desc = this.objective.description,
    reward = this.reward.toDM(),
)
