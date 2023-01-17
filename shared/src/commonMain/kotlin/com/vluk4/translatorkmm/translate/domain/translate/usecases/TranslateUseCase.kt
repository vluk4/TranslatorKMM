package com.vluk4.translatorkmm.translate.domain.translate.usecases

import com.vluk4.translatorkmm.core.domain.language.Language
import com.vluk4.translatorkmm.core.domain.util.Resource
import com.vluk4.translatorkmm.translate.domain.translate.TranslateClient
import com.vluk4.translatorkmm.translate.domain.translate.TranslateException
import com.vluk4.translatorkmm.translate.domain.translate.history.HistoryDataSource
import com.vluk4.translatorkmm.translate.domain.translate.history.HistoryItem

class TranslateUseCase(
    private val client: TranslateClient,
    private val historyDataSource: HistoryDataSource
) {

    suspend fun execute(
        fromLanguage: Language,
        fromText: String,
        toLanguage: Language
    ): Resource<String> {
        return try {
            val translatedText = client.translate(
                fromLanguage, fromText, toLanguage
            )

            historyDataSource.insertHistoryItem(
                HistoryItem(
                    id = null,
                    fromLanguageCode = fromLanguage.langCode,
                    fromText = fromText,
                    toLanguageCode = toLanguage.langCode,
                    toText = translatedText
                )
            )

            Resource.Success(translatedText)
        } catch (e: TranslateException) {
            e.printStackTrace()
            Resource.Error(e)
        }
    }
}