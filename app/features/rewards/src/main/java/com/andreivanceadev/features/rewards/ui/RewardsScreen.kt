package com.andreivanceadev.features.rewards.ui

import android.os.Parcelable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.andreivanceadev.designsystem.composables.ImageCard
import com.andreivanceadev.designsystem.theme.ObjectiveRewardsTheme
import com.andreivanceadev.designsystem.theme.Spacing
import com.andreivanceadev.features.rewards.R
import com.andreivanceadev.features.rewards.viewmodel.RewardsViewState
import kotlinx.parcelize.Parcelize

@Preview(showSystemUi = true)
@Composable
private fun PreviewRewardsScreenNoContent() {
    ObjectiveRewardsTheme {
        val mockViewState = RewardsViewState.NoContent
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
        ) {
            RewardsInternal(mockViewState)
        }
    }
}

val mockViewState = RewardsViewState.Content(
    categories = listOf(
        RewardsViewState.Content.RewardCategory("Relationships & Social"),
        RewardsViewState.Content.RewardCategory("Personal Development"),
        RewardsViewState.Content.RewardCategory("Fun"),
        RewardsViewState.Content.RewardCategory("Health"),
    ),
    rewards = listOf(
        RewardsViewState.Content.Reward(title = "Title 1", imagePath = "Image1", category = "Relationships & Social"),
        RewardsViewState.Content.Reward(title = "Title 2", imagePath = "Image1", category = "Relationships & Social"),
        RewardsViewState.Content.Reward(title = "Title 3", imagePath = "Image1", category = "Personal Development"),
        RewardsViewState.Content.Reward(title = "Title 4", imagePath = "Image1", category = "Fun"),
        RewardsViewState.Content.Reward(title = "Title 5", imagePath = "Image1", category = "Fun"),
        RewardsViewState.Content.Reward(title = "Title 6", imagePath = "Image1", category = "Fun"),
    ),
)

@Preview(showSystemUi = true)
@Composable
private fun PreviewRewardsScreen() {
    ObjectiveRewardsTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
        ) {
            RewardsInternal(mockViewState)
        }
    }
}

@Composable
fun RewardsScreen() {
    RewardsInternal(mockViewState)
}

@Composable
fun NoRewardsContent(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "No rewards yet. Add rewards to see them here...",
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun RewardsInternal(
    state: RewardsViewState,
    onAddNewObjective: () -> Unit = {},
) {
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        when (state) {
            RewardsViewState.NoContent -> NoRewardsContent()
            is RewardsViewState.Content -> {
                LazyColumn {
                    state.categories.forEachIndexed { index, category ->
                        item {
                            ListHeader(displayName = category.categoryName)
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                        val categoryRewards = state.rewards.filter { reward -> reward.category == category.categoryName }
                        if (categoryRewards.isEmpty()) {
                            item {
                                NoRewardsForCategory()
                            }
                        } else {
                            items(categoryRewards) { item ->
                                RewardItem(data = item)
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                        }
                    }
                }
            }
        }
        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(Spacing.x2),
            onClick = onAddNewObjective,
        ) {
            Icon(
                imageVector = Icons.Rounded.Add,
                contentDescription = stringResource(R.string.btn_add_new_reward),
            )
        }
    }
}

@Composable
fun NoRewardsForCategory(
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier) {
        Text(text = "You have no rewards in this category...")
    }
}

@Composable
fun RewardItem(data: RewardsViewState.Content.Reward) {
    ImageCard(
        title = data.title,
        description = data.imagePath,
        imageUri = null,
        onClick = {},
    )
}

@Composable
fun ListHeader(
    displayName: String,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(text = displayName)
        Divider(modifier = Modifier.padding(vertical = 2.dp), thickness = 1.dp)
    }
}

@Parcelize
private sealed class ListItem : Parcelable {
    data class Header(val displayName: String) : ListItem()
    data class Reward(val data: RewardsViewState.Content.Reward) : ListItem()
}
