package com.vluk4.translatorkmm.android.di

import android.app.Application
import com.squareup.sqldelight.db.SqlDriver
import com.vluk4.translatorkmm.database.TranslateDatabase
import com.vluk4.translatorkmm.translate.data.history.SqlDelightHistoryDataSource
import com.vluk4.translatorkmm.translate.data.local.DatabaseDriverFactory
import com.vluk4.translatorkmm.translate.data.remote.HttpClientFactory
import com.vluk4.translatorkmm.translate.data.translate.TranslateClientImpl
import com.vluk4.translatorkmm.translate.domain.translate.usecases.TranslateUseCase
import com.vluk4.translatorkmm.translate.domain.translate.TranslateClient
import com.vluk4.translatorkmm.translate.domain.translate.history.HistoryDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {
        return HttpClientFactory().create()
    }

    @Provides
    @Singleton
    fun provideTranslateClient(httpClient: HttpClient): TranslateClient {
        return TranslateClientImpl(httpClient)
    }

    @Provides
    @Singleton
    fun provideDatabaseDriver(app: Application): SqlDriver {
        return DatabaseDriverFactory(app).create()
    }

    @Provides
    @Singleton
    fun provideHistoryDataSource(driver: SqlDriver): HistoryDataSource {
        return SqlDelightHistoryDataSource(TranslateDatabase(driver))
    }

    @Provides
    @Singleton
    fun provideTranslateUseCase(
        client: TranslateClient,
        dataSource: HistoryDataSource
    ): TranslateUseCase {
        return TranslateUseCase(client, dataSource)
    }
}