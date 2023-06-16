package com.andreivanceadev.designsystem.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.andreivanceadev.designsystem.theme.ObjectiveRewardsTheme

@Preview(showSystemUi = true, showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
private fun PreviewPlaceholderScreen() {
    ObjectiveRewardsTheme {
        PlaceholderScreen(screenName = "Placeholder Preview")
    }
}

@Composable
fun PlaceholderScreen(screenName: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = screenName)
    }
}