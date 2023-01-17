package com.vluk4.translatorkmm.translate.data.translate

import com.vluk4.translatorkmm.core.domain.language.Language
import com.vluk4.translatorkmm.translate.domain.translate.TranslateClient
import com.vluk4.translatorkmm.translate.domain.translate.TranslateError
import com.vluk4.translatorkmm.translate.domain.translate.TranslateException
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.utils.io.errors.*

class TranslateClientImpl(
    private val httpClient: HttpClient
) : TranslateClient {
    override suspend fun translate(
        fromLanguage: Language,
        fromText: String,
        toLanguage: Language
    ): String {
        val result = try {
            httpClient.post {
                url(urlString = "https://translate.pl-coding.com/translate")
                contentType(ContentType.Application.Json)
                setBody(
                    TranslateDto(
                        textToTranslate = fromText,
                        sourceLanguageCode = fromLanguage.langCode,
                        targetLanguageCode = toLanguage.langCode
                    )
                )
            }
        } catch (e: IOException) {
            throw TranslateException(TranslateError.SERVICE_UNAVAILABLE)
        }

        when (result.status.value) {
            in 200..299 -> Unit
            in 400..499 -> throw TranslateException(TranslateError.CLIENT_ERROR)
            500 -> throw TranslateException(TranslateError.SERVER_ERROR)
            else -> throw TranslateException(TranslateError.UNKNOWN_ERROR)
        }

        return runCatching {
            result.body<TranslatedDto>().translatedText
        }.getOrElse {
            throw TranslateException(TranslateError.SERVER_ERROR)
        }
    }
}