package com.vluk4.translatorkmm.translate.data.local

import android.content.Context
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import com.vluk4.translatorkmm.database.TranslateDatabase

actual class DatabaseDriverFactory(
    private val context: Context
) {
    actual fun create(): SqlDriver {
        return AndroidSqliteDriver(TranslateDatabase.Schema, context, "translate.db")
    }
}