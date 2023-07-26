package com.andreivanceadev.data.datamodel

import com.andreivanceadev.data.entity.ObjectiveAndRewardDto
import com.andreivanceadev.data.entity.ObjectiveDto

data class ObjectiveDM(
    val title: String,
    val desc: String,
    val reward: RewardDM
)

internal fun ObjectiveDM.toDto() = ObjectiveDto(
    title = this.title,
    description = this.desc
)

internal fun ObjectiveAndRewardDto.toDM() = ObjectiveDM(
    title = this.objective.title,
    desc = this.objective.description,
    reward = this.reward.toDM()
)
