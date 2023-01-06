package com.vluk4.translatorkmm.core.domain.util


import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

expect class CommonStateFlow<T>(flow: StateFlow<T>): StateFlow<T>

fun <T> Flow<T>.toCommonStateFlow() = CommonFlow(this )