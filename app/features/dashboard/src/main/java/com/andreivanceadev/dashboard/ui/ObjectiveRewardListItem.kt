package com.andreivanceadev.dashboard.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.andreivanceadev.designsystem.theme.ObjectiveRewardsTheme
import com.andreivanceadev.designsystem.theme.Spacing
import com.andreivanceadev.objectiverewards.dashboard.R


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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ObjectiveRewardListItem(
    objectiveTitle: String,
    objectiveDescription: String,
    @DrawableRes defaultRewardImage: Int = R.drawable.drunken_clam,
    rewardImageUrl: String? = null,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        onClick = onClick
    ) {
        val model = ImageRequest.Builder(LocalContext.current)
            .data(rewardImageUrl)
            .size(Size.ORIGINAL)
            .fallback(defaultRewardImage)
            .error(defaultRewardImage)
            .build()
        val painter = rememberAsyncImagePainter(model = model)
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(Spacing.x1_5))
                .height(160.dp),
            painter = painter,
            contentScale = ContentScale.FillWidth,
            contentDescription = null
        )
        Column(modifier = Modifier.padding(Spacing.x2)) {
            Text(
                text = objectiveTitle,
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(Spacing.x1))
            Text(
                text = objectiveDescription,
                style = MaterialTheme.typography.bodySmall
            )

        }
    }
}