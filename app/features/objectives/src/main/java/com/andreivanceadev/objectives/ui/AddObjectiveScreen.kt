package com.andreivanceadev.objectives.ui

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.andreivanceadev.designsystem.composables.ImageCard
import com.andreivanceadev.designsystem.theme.ObjectiveRewardsTheme
import com.andreivanceadev.designsystem.theme.Spacing
import com.andreivanceadev.objectiverewards.dashboard.R
import kotlinx.coroutines.launch

@Preview(showSystemUi = true, backgroundColor = 0xFFFFFF, showBackground = true)
@Composable
private fun PreviewAddObjectiveScreen() {
    ObjectiveRewardsTheme {
        AddObjectiveScreen()
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AddObjectiveScreen(
    onFinish: () -> Unit = {}
) {

    var objectiveTitle by rememberSaveable { mutableStateOf("") }
    var objectiveDescription by rememberSaveable { mutableStateOf("") }
    val maxTitleLength = 30
    val maxDescLength = 250

    val scrollState = rememberScrollState()
    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val scope = rememberCoroutineScope()

    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val launcher = rememberLauncherForActivityResult(
        contract =
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        imageUri = uri
    }

    Column(
        modifier = Modifier
            .padding(Spacing.x2)
            .verticalScroll(state = scrollState)
            .bringIntoViewRequester(bringIntoViewRequester),
    ) {
        ImageCard(
            title = objectiveTitle,
            description = objectiveDescription,
            imageUri = imageUri,
            onClick = {},
            contentOverImage = {
                ElevatedButton(
                    modifier = Modifier.align(Alignment.Center),
                    onClick = {
                        launcher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    }) {
                    Text(text = stringResource(R.string.add_image))
                }
            }
        )

        Spacer(modifier = Modifier.height(Spacing.x1))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .onFocusEvent { focusState ->
                    if (focusState.isFocused) {
                        scope.launch { bringIntoViewRequester.bringIntoView() }
                    }
                },
            value = objectiveTitle,
            onValueChange = { newValue ->
                if (newValue.length <= maxTitleLength) {
                    objectiveTitle = newValue
                }
            },
            label = { Text(text = stringResource(id = R.string.objective_title)) },
            singleLine = true,
            supportingText = { Text(text = "${objectiveTitle.length}/$maxTitleLength") }
        )

        Spacer(modifier = Modifier.height(Spacing.x1))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .onFocusEvent { focusState ->
                    if (focusState.isFocused) {
                        scope.launch { bringIntoViewRequester.bringIntoView() }
                    }
                },
            value = objectiveDescription,
            onValueChange = { newValue ->
                if (newValue.length <= maxDescLength) {
                    objectiveDescription = newValue
                }
            },
            label = { Text(text = stringResource(id = R.string.objective_desc)) },
            minLines = 3,
            supportingText = { Text(text = "${objectiveDescription.length}/$maxDescLength") }
        )

        Spacer(modifier = Modifier.height(Spacing.x2))

        ElevatedButton(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            onClick = {
                //todo add logic to save the new objective in db
                onFinish()
            }) {
            Text(text = stringResource(R.string.save))
        }

    }
}