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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.andreivanceadev.dashboard.viewmodel.DashboardViewModel
import com.andreivanceadev.dashboard.viewmodel.DashboardViewState
import com.andreivanceadev.designsystem.theme.ObjectiveRewardsTheme
import com.andreivanceadev.designsystem.theme.Spacing

@Preview(showSystemUi = true, showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
private fun PreviewDashboardScreen() {
    ObjectiveRewardsTheme {
        DashboardScreen(
            state = DashboardViewState.NoContent,
            onAddNewObjective = {},
            onViewObjectiveDetails = {}
        )
    }
}


@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = hiltViewModel(),
    onAddNewObjective: () -> Unit,
    onViewObjectiveDetails: (id: Long) -> Unit
) {

    val state = viewModel.container.stateFlow.collectAsState().value

    LaunchedEffect(key1 = Unit) {
        viewModel.onStart()
    }

    DashboardScreen(
        state = state,
        onAddNewObjective = onAddNewObjective,
        onViewObjectiveDetails = onViewObjectiveDetails
    )
}

@Composable
internal fun DashboardScreen(
    state: DashboardViewState,
    onAddNewObjective: () -> Unit,
    onViewObjectiveDetails: (id: Long) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(Spacing.x1)
    ) {

        when (state) {
            is DashboardViewState.Content -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(Spacing.x1)
                ) {
                    items(state.content) { objective ->
                        GoalListItem(
                            objectiveTitle = objective.title,
                            objectiveDescription = objective.desc,
                            rewardImageUrl = objective.reward.imagePath,
                            onClick = { onViewObjectiveDetails(objective.id) }
                        )
                    }
                }
            }

            DashboardViewState.NoContent -> {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = "No objectives so far..."
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