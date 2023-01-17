package com.vluk4.translatorkmm.android.translate.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vluk4.translatorkmm.translate.domain.translate.usecases.TranslateUseCase
import com.vluk4.translatorkmm.translate.domain.translate.history.HistoryDataSource
import com.vluk4.translatorkmm.translate.presentation.TranslateEvent
import com.vluk4.translatorkmm.translate.presentation.TranslateViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AndroidTranslateViewModel @Inject constructor(
    private val translateUseCase: TranslateUseCase,
    private val historyDataSource: HistoryDataSource
): ViewModel() {

    private val viewModel by lazy {
        TranslateViewModel(
            translateUseCase = translateUseCase,
            historyDataSource = historyDataSource,
            coroutineScope = viewModelScope
        )
    }

    val state = viewModel.state

    fun onEvent(event: TranslateEvent) {
        viewModel.onEvent(event)
    }
}