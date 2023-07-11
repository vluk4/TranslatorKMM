package com.vluk4.translatorkmm.translate.data.translate.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TranslateDto(
    @SerialName("data")
    val translation: Translation,
    @SerialName("status")
    val status: String
)