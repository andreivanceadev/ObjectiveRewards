package com.andreivanceadev.dashboard.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.andreivanceadev.designsystem.theme.ObjectiveRewardsTheme
import com.andreivanceadev.designsystem.theme.Spacing

@Preview(showSystemUi = true, showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
private fun PreviewDashboardScreen() {
    ObjectiveRewardsTheme {
        DashboardScreen() {}
    }
}

@Composable
fun DashboardScreen(
    onAddNewObjective: () -> Unit
) {

    val mockObjectiveRewards = listOf(
        "Objective 1" to "Objective Description 1",
        "Objective 2" to "Objective Description 2",
        "Objective 3" to "Objective Description 3",
        "Objective 4" to "Objective Description 4",
        "Objective 5" to "Objective Description 5",
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(Spacing.x1)
    ) {

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(Spacing.x1)
        ) {
            items(mockObjectiveRewards) { pair ->
                ObjectiveRewardListItem(
                    objectiveTitle = pair.first,
                    objectiveDescription = pair.second,
                    rewardImageUrl = "https://img3.hulu.com/user/v3/artwork/40c7961d-3d92-4b2c-bbe7-2a48ca2926f5?base_image_bucket_name=image_manager&base_image=9bf724e4-93ef-40d7-9720-5d8a08521e57&size=600x338&format=jpeg"
                )
            }
        }

        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(Spacing.x2),
            onClick = onAddNewObjective
        ) {
            Icon(
                imageVector = Icons.Rounded.Add,
                contentDescription = "Add new objective"
            )
        }
    }

}