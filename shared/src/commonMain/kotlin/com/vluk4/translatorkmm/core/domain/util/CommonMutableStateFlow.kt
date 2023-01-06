package com.vluk4.translatorkmm.core.domain.util


import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

expect class CommonMutableStateFlow<T>(flow: MutableStateFlow<T>): MutableStateFlow<T>

fun <T> Flow<T>.toCommonMutableStateFlow() = CommonFlow(this)