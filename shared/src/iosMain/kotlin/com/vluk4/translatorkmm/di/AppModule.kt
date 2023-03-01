package com.vluk4.translatorkmm.di

import com.vluk4.translatorkmm.database.TranslateDatabase
import com.vluk4.translatorkmm.translate.data.history.SqlDelightHistoryDataSource
import com.vluk4.translatorkmm.translate.data.local.DatabaseDriverFactory
import com.vluk4.translatorkmm.translate.data.remote.HttpClientFactory
import com.vluk4.translatorkmm.translate.data.translate.KtorTranslateClient
import com.vluk4.translatorkmm.translate.domain.translate.Translate
import com.vluk4.translatorkmm.translate.domain.translate.TranslateClient
import com.vluk4.translatorkmm.translate.domain.translate.history.HistoryDataSource
import io.ktor.client.*

class AppModule {

    val historyDataSource: HistoryDataSource by lazy {
        SqlDelightHistoryDataSource(
            TranslateDatabase(
                DatabaseDriverFactory().create()
            )
        )
    }

    private val translateClient: TranslateClient by lazy {
        KtorTranslateClient(
            HttpClientFactory().create()
        )
    }

    val translateUseCase: Translate by lazy {
        Translate(translateClient, historyDataSource)
    }
}