package com.andreivanceadev.features.rewards.viewmodel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class RewardsViewState {

    data object NoContent : RewardsViewState()

    @Parcelize
    data class Content(
        val categories: List<RewardCategory>,
        val rewards: List<Reward>,
    ) : RewardsViewState(), Parcelable {

        @Parcelize
        data class RewardCategory(
            val categoryName: String,
        ) : Parcelable

        @Parcelize
        data class Reward(
            val title: String,
            val imagePath: String,
            val category: String,
        ) : Parcelable
    }
}
