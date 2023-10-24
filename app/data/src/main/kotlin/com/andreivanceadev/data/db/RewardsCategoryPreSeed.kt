package com.andreivanceadev.data.db

import com.andreivanceadev.data.entity.RewardCategoryEntity

internal object RewardsCategoryPreSeed {

    const val version = "1"

    val entries = listOf(
        RewardCategoryEntity(displayName = "Fun"),
        RewardCategoryEntity(displayName = "Health"),
        RewardCategoryEntity(displayName = "Family"),
        RewardCategoryEntity(displayName = "Relationship"),
        RewardCategoryEntity(displayName = "Coaching"),
        RewardCategoryEntity(displayName = "Other"),
    )

}
