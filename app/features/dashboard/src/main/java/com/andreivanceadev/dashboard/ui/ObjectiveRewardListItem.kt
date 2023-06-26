package com.andreivanceadev.dashboard.ui

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.andreivanceadev.designsystem.composables.ImageCard
import com.andreivanceadev.designsystem.theme.ObjectiveRewardsTheme
import com.andreivanceadev.designsystem.theme.Spacing


@Preview(showSystemUi = true)
@Composable
private fun PreviewObjectiveRewardListItem() {
    ObjectiveRewardsTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .height(500.dp)
                .padding(Spacing.x1)
        ) {
            ObjectiveRewardListItem(
                objectiveTitle = "Test Objective",
                objectiveDescription = "Test Objective description. This is the description of what you are trying to achieve"
            )
        }
    }
}

@Composable
fun ObjectiveRewardListItem(
    objectiveTitle: String,
    objectiveDescription: String,
    rewardImageUrl: String? = null,
    onClick: () -> Unit = {}
) {
    val imageUri = remember(rewardImageUrl) {
        rewardImageUrl?.let { Uri.parse(it) }
    }

    ImageCard(
        title = objectiveTitle,
        description = objectiveDescription,
        imageUri = imageUri,
        onClick = onClick,
    )

}