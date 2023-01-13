package com.vluk4.translatorkmm.android.voicetotext.presentation

import android.Manifest
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material.icons.rounded.Stop
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.vluk4.translatorkmm.android.R
import com.vluk4.translatorkmm.android.core.theme.LightBlue
import com.vluk4.translatorkmm.android.voicetotext.presentation.components.VoiceRecorderDisplay
import com.vluk4.translatorkmm.voicetotext.presentation.DisplayState.*
import com.vluk4.translatorkmm.voicetotext.presentation.VoiceToTextEvent
import com.vluk4.translatorkmm.voicetotext.presentation.VoiceToTextState

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun VoiceToTextScreen(
    state: VoiceToTextState,
    languageCode: String,
    onResult: (String) -> Unit,
    onEvent: (VoiceToTextEvent) -> Unit
) {
    val context = LocalContext.current
    val recordAudioLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            onEvent(
                VoiceToTextEvent.PermissionResult(
                    isGranted = isGranted,
                    isPermanentlyDeclined = !isGranted && !(context as ComponentActivity)
                        .shouldShowRequestPermissionRationale(Manifest.permission.RECORD_AUDIO)
                )
            )
        }
    )

    LaunchedEffect(key1 = recordAudioLauncher) {
        recordAudioLauncher.launch(Manifest.permission.RECORD_AUDIO)
    }

    Scaffold(
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            MicFab(state, onEvent, languageCode, onResult)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                IconButton(
                    onClick = { onEvent(VoiceToTextEvent.Close) },
                    modifier = Modifier.align(Alignment.CenterStart)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Close,
                        contentDescription = stringResource(id = R.string.close)
                    )
                }
                if (state.displayState == SPEAKING) {
                    Text(
                        text = stringResource(id = R.string.listening),
                        color = LightBlue,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .padding(bottom = 100.dp)
                    .weight(1f)
                    .verticalScroll(
                        rememberScrollState()
                    ),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AnimatedContent(targetState = state.displayState) { displayState ->
                    when (displayState) {
                        WAITING_TO_TALK -> {
                            Text(
                                text = stringResource(id = R.string.start_talking),
                                style = MaterialTheme.typography.h2,
                                textAlign = TextAlign.Center
                            )
                        }
                        SPEAKING -> {
                            VoiceRecorderDisplay(
                                powerRatio = state.powerRatios,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(100.dp)
                            )
                        }
                        DISPLAYING_RESULTS -> {
                            Text(
                                text = state.spokenText,
                                style = MaterialTheme.typography.h2,
                                textAlign = TextAlign.Center
                            )
                        }
                        ERROR -> {
                            Text(
                                text = state.recordError ?: "Unknown error",
                                style = MaterialTheme.typography.h2,
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colors.error
                            )
                        }
                        else -> Unit
                    }
                }
            }
        }
    }
}

@Composable
@OptIn(ExperimentalAnimationApi::class)
private fun MicFab(
    state: VoiceToTextState,
    onEvent: (VoiceToTextEvent) -> Unit,
    languageCode: String,
    onResult: (String) -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        FloatingActionButton(
            onClick = {
                if (state.displayState != DISPLAYING_RESULTS) {
                    onEvent(VoiceToTextEvent.ToggleRecording(languageCode))
                } else {
                    onResult(state.spokenText)
                }
            },
            backgroundColor = MaterialTheme.colors.primary,
            contentColor = MaterialTheme.colors.onPrimary,
            modifier = Modifier.size(75.dp)
        ) {
            AnimatedContent(targetState = state.displayState) {
                when (state.displayState) {
                    SPEAKING -> {
                        Icon(
                            imageVector = Icons.Rounded.Close,
                            contentDescription = stringResource(id = R.string.stop_recording),
                            modifier = Modifier.size(50.dp)
                        )
                    }
                    DISPLAYING_RESULTS -> {
                        Icon(
                            imageVector = Icons.Rounded.Check,
                            contentDescription = stringResource(id = R.string.apply),
                            modifier = Modifier.size(50.dp)
                        )
                    }
                    else -> {
                        Icon(
                            painter = painterResource(id = R.drawable.mic),
                            contentDescription = stringResource(id = R.string.record_audio),
                            modifier = Modifier.size(50.dp)
                        )
                    }
                }
            }
        }
        if (state.displayState == DISPLAYING_RESULTS) {
            IconButton(onClick = {
                onEvent(VoiceToTextEvent.ToggleRecording(languageCode))
            }) {
                Icon(
                    imageVector = Icons.Rounded.Refresh,
                    contentDescription = stringResource(id = R.string.record_again),
                    tint = LightBlue
                )
            }
        }
    }
}