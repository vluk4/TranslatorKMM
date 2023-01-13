package com.vluk4.translatorkmm.android.voicetotext.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vluk4.translatorkmm.voicetotext.domain.VoiceToTextParser
import com.vluk4.translatorkmm.voicetotext.presentation.VoiceToTextEvent
import com.vluk4.translatorkmm.voicetotext.presentation.VoiceToTextViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AndroidVoiceToTextViewModel @Inject constructor(
    private val parser: VoiceToTextParser
): ViewModel() {

    private val viewModel by lazy {
        VoiceToTextViewModel(
            parser = parser,
            coroutineScope = viewModelScope
        )
    }

    val state = viewModel.state

    fun onEvent(event: VoiceToTextEvent) {
        viewModel.onEvent(event)
    }
}