package com.vluk4.translatorkmm

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform