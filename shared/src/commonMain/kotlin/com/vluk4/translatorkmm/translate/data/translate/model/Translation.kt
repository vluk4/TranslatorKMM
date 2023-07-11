package com.vluk4.translatorkmm.translate.data.translate.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Translation(
    @SerialName("translatedText")
    val translatedText: String
)