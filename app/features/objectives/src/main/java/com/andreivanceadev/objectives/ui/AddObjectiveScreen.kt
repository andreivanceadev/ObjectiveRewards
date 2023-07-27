package com.andreivanceadev.objectives.ui

import android.content.Intent
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import com.andreivanceadev.core.state.effectFlow
import com.andreivanceadev.designsystem.composables.ImageCard
import com.andreivanceadev.designsystem.theme.ObjectiveRewardsTheme
import com.andreivanceadev.designsystem.theme.Spacing
import com.andreivanceadev.objectiverewards.objectives.R
import com.andreivanceadev.objectives.viewmodel.AddObjectiveSideEffect
import com.andreivanceadev.objectives.viewmodel.AddObjectiveViewModel
import kotlinx.coroutines.launch

@Preview(showSystemUi = true, backgroundColor = 0xFFFFFF, showBackground = true)
@Composable
private fun PreviewAddObjectiveScreen() {
    ObjectiveRewardsTheme {
        AddObjectiveScreen(
            objectiveTitle = "Preview objective",
            objectiveDescription = "Preview description",
            imageUrl = null,
            onTitleChange = {},
            onImageChange = {},
            onDescriptionChange = {},
            onSave = {},
        )
    }
}

@Composable
fun AddObjectiveScreen(
    viewModel: AddObjectiveViewModel = hiltViewModel(),
    onNavBack: () -> Unit,
) {
    val state = viewModel.container.stateFlow.collectAsState().value

    LaunchedEffect(key1 = Unit) {
        viewModel.effectFlow.collect { effect ->
            when (effect) {
                AddObjectiveSideEffect.NavBack -> onNavBack()
            }
        }
    }

    AddObjectiveScreen(
        onSave = viewModel::onSave,
        onImageChange = viewModel::onImageChange,
        onTitleChange = viewModel::onTitleChange,
        onDescriptionChange = viewModel::onDescriptionChange,
        objectiveTitle = state.title,
        objectiveDescription = state.description,
        imageUrl = state.imagePath,
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun AddObjectiveScreen(
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onImageChange: (String) -> Unit,
    onSave: () -> Unit,
    objectiveTitle: String,
    objectiveDescription: String,
    imageUrl: String?,
) {
    val maxTitleLength = 30
    val maxDescLength = 250

    val scrollState = rememberScrollState()
    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val scope = rememberCoroutineScope()

    val imageUri = remember(imageUrl) {
        imageUrl?.toUri()
    }

    val context = LocalContext.current

    val imagePicker = rememberLauncherForActivityResult(
        contract =
        ActivityResultContracts.PickVisualMedia(),
    ) { uri: Uri? ->
        uri?.let {
            // grant access for later read (when app starts)
            val flag = Intent.FLAG_GRANT_READ_URI_PERMISSION
            context.contentResolver.takePersistableUriPermission(it, flag)
        }
        uri?.toString()?.let(onImageChange)
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
                        imagePicker.launch(
                            PickVisualMediaRequest(
                                ActivityResultContracts.PickVisualMedia.ImageOnly,
                            ),
                        )
                    },
                ) {
                    Text(text = stringResource(R.string.add_image))
                }
            },
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
                    onTitleChange(newValue)
                }
            },
            label = { Text(text = stringResource(id = R.string.objective_title)) },
            singleLine = true,
            supportingText = { Text(text = "${objectiveTitle.length}/$maxTitleLength") },
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
                    onDescriptionChange(newValue)
                }
            },
            label = { Text(text = stringResource(id = R.string.objective_desc)) },
            minLines = 3,
            supportingText = { Text(text = "${objectiveDescription.length}/$maxDescLength") },
        )

        Spacer(modifier = Modifier.height(Spacing.x2))

        ElevatedButton(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            onClick = {
                onSave()
            },
        ) {
            Text(text = stringResource(R.string.save))
        }
    }
}
