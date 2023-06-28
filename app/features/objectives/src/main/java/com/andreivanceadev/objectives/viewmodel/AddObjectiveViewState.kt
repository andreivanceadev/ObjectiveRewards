package com.andreivanceadev.objectives.viewmodel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AddObjectiveViewState(
    val title: String = "",
    val description: String = "",
    val imagePath: String = ""
) : Parcelable