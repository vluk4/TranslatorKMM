package com.vluk4.translatorkmm.di

import com.vluk4.translatorkmm.translate.data.local.FakeHistoryDataSource
import com.vluk4.translatorkmm.translate.data.remote.FakeTranslateClient
import com.vluk4.translatorkmm.translate.domain.translate.usecases.TranslateUseCase
import com.vluk4.translatorkmm.translate.domain.translate.TranslateClient
import com.vluk4.translatorkmm.translate.domain.translate.history.HistoryDataSource
import com.vluk4.translatorkmm.voicetotext.data.FakeVoiceToTextParser
import com.vluk4.translatorkmm.voicetotext.domain.VoiceToTextParser
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class TestAppModule {

    @Provides
    @Singleton
    fun provideFakeTranslateClient(): TranslateClient {
        return FakeTranslateClient()
    }

    @Provides
    @Singleton
    fun provideFakeHistoryDataSource(): HistoryDataSource {
        return FakeHistoryDataSource()
    }

    @Provides
    @Singleton
    fun provideFakeTranslateUseCase(
        client: TranslateClient,
        dataSource: HistoryDataSource
    ): TranslateUseCase {
        return TranslateUseCase(client = client, historyDataSource = dataSource)
    }

    @Provides
    @Singleton
    fun provideFakeVoiceToTextParser(): VoiceToTextParser {
        return FakeVoiceToTextParser()
    }


}