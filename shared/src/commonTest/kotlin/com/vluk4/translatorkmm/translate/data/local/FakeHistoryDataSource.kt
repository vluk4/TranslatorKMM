package com.vluk4.translatorkmm.translate.data.local

import com.vluk4.translatorkmm.core.domain.util.CommonFlow
import com.vluk4.translatorkmm.core.domain.util.toCommonFlow
import com.vluk4.translatorkmm.translate.domain.translate.history.HistoryDataSource
import com.vluk4.translatorkmm.translate.domain.translate.history.HistoryItem
import kotlinx.coroutines.flow.MutableStateFlow

class FakeHistoryDataSource: HistoryDataSource {

    private val _data = MutableStateFlow<List<HistoryItem>>(emptyList())

    override fun getHistory(): CommonFlow<List<HistoryItem>> {
        return _data.toCommonFlow()
    }

    override suspend fun insertHistoryItem(item: HistoryItem) {
        _data.value += item
    }
}