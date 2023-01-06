package com.vluk4.translatorkmm.translate.data.local

import com.squareup.sqldelight.db.SqlDriver

expect class DatabaseDriverFactory {
    fun create(): SqlDriver
}