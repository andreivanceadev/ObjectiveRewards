package com.andreivanceadev.dashboard.viewmodel

import android.os.Parcelable
import com.andreivanceadev.data.datamodel.ObjectiveDM
import com.andreivanceadev.data.datamodel.RewardDM
import kotlinx.parcelize.Parcelize

sealed class DashboardViewState {

    object NoContent : DashboardViewState()

    @Parcelize
    data class Content(
        val content: List<Objective> = emptyList()
    ) : DashboardViewState(), Parcelable {
        @Parcelize
        data class Objective(
            val title: String,
            val desc: String,
            val reward: Reward
        ) : Parcelable

        @Parcelize
        data class Reward(
            val title: String,
            val imagePath: String
        ) : Parcelable
    }
}

fun ObjectiveDM.toUI() = DashboardViewState.Content.Objective(
    title = this.title,
    desc = this.desc,
    reward = this.reward.toUI()
)

private fun RewardDM.toUI() = DashboardViewState.Content.Reward(
    title = this.title,
    imagePath = this.imagePath
)

