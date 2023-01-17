package com.vluk4.translatorkmm.translate.data.remote

import com.vluk4.translatorkmm.core.domain.language.Language
import com.vluk4.translatorkmm.translate.domain.translate.TranslateClient

class FakeTranslateClient: TranslateClient {

    var translatedText = "test translation"

    override suspend fun translate(
        fromLanguage: Language,
        fromText: String,
        toLanguage: Language
    ): String {
        return translatedText
    }
}