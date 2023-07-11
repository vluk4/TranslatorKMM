package com.vluk4.translatorkmm.translate.data.translate

import com.vluk4.translatorkmm.core.domain.language.Language
import com.vluk4.translatorkmm.translate.data.translate.model.TranslateDto
import com.vluk4.translatorkmm.translate.domain.translate.TranslateClient
import com.vluk4.translatorkmm.translate.domain.translate.TranslateError
import com.vluk4.translatorkmm.translate.domain.translate.TranslateException
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.FormDataContent
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

        val url = "https://text-translator2.p.rapidapi.com/translate"

        val requestBody = listOf(
            "source_language" to fromLanguage.langCode,
            "target_language" to toLanguage.langCode,
            "text" to fromText
        )

        val result = try {
            httpClient.post(urlString = url) {
                headers {
                    append(
                        HttpHeaders.ContentType,
                        ContentType.Application.FormUrlEncoded.toString()
                    )
                    append("X-RapidAPI-Key", "84d76fadbemshbc7172ab5557c6ep1e2522jsnfd43e049d804")
                    append("X-RapidAPI-Host", "text-translator2.p.rapidapi.com")
                }
                setBody(
                    FormDataContent(Parameters.build {
                        requestBody.forEach { (name, value) ->
                            append(name, value)
                        }
                    })
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
            result.body<TranslateDto>().translation.translatedText
        }.getOrElse {
            throw TranslateException(TranslateError.SERVER_ERROR)
        }
    }
}