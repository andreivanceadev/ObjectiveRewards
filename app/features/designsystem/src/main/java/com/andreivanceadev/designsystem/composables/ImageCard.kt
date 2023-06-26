package com.andreivanceadev.designsystem.composables

import android.net.Uri
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.andreivanceadev.designsystem.theme.ObjectiveRewardsTheme
import com.andreivanceadev.designsystem.theme.Spacing
import com.andreivanceadev.objectiverewards.designsystem.R

object ImageCardDefaults {
    val cardElevation = 1.dp
    val imageHeight = 192.dp
}

@Preview
@Composable
private fun PreviewImageCard() {
    ObjectiveRewardsTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            ImageCard(
                title = "Preview Card Title",
                description = "Preview Card Description",
                imageUri = null,
                onClick = {}
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageCard(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    imageUri: Uri?,
    @DrawableRes fallbackImage: Int = R.drawable.drunken_clam,
    contentOverImage: @Composable BoxScope.() -> Unit = {},
    onClick: () -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = ImageCardDefaults.cardElevation),
        onClick = onClick
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(Spacing.x1_5))
                .height(ImageCardDefaults.imageHeight)
                .background(
                    brush = Brush.linearGradient(
                        listOf(
                            Color.Red,
                            Color.White,
                            Color.Green,
                            Color.Blue,
                            Color.Black
                        )
                    ), alpha = 0.5f
                )
        ) {
            imageUri?.let {
                val model = ImageRequest.Builder(LocalContext.current)
                    .data(it)
                    .size(Size.ORIGINAL)
                    .fallback(fallbackImage)
                    .error(fallbackImage)
                    .build()
                val painter = rememberAsyncImagePainter(model = model)
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(Spacing.x1_5))
                        .height(ImageCardDefaults.imageHeight),
                    painter = painter,
                    contentScale = ContentScale.FillWidth,
                    contentDescription = null
                )
            }

            contentOverImage()
        }

        Column(modifier = Modifier.padding(Spacing.x2)) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(Spacing.x1))
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall
            )

        }

    }
}