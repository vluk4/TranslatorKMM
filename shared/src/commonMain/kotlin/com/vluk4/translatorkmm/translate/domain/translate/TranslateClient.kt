package com.vluk4.translatorkmm.translate.domain.translate

import com.vluk4.translatorkmm.core.domain.language.Language

interface TranslateClient {
    suspend fun translate(
        fromLanguage: Language,
        fromText: String,
        toLanguage: Language
    ): String
}