package com.vluk4.translatorkmm.translate.domain.translate.history

import com.vluk4.translatorkmm.core.domain.util.CommonFlow

interface HistoryDataSource {
    fun getHistory(): CommonFlow<List<HistoryItem>>
    suspend fun insertHistoryItem(item: HistoryItem)
}