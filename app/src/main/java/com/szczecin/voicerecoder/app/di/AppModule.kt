package com.szczecin.voicerecoder.app.di

import android.app.Application
import android.content.Context
import com.szczecin.voicerecoder.app.common.rx.RxSchedulers
import com.szczecin.voicerecoder.data.database.dao.VoiceRecorderDao
import com.szczecin.voicerecoder.data.database.repo.ProviderDataBase
import com.szczecin.voicerecoder.data.database.repo.VoiceRecorderDatabase
import com.szczecin.voicerecoder.data.database.repo.VoiceRecorderRoomDatabase
import com.szczecin.voicerecoder.data.database.storage.VoiceRecorderStorage
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideContext(application: Application): Context = application

    @Singleton
    @Provides
    fun provideSchedulers(): RxSchedulers = RxSchedulers()

    @Singleton
    @Provides
    fun provideDatabase(context: Context): VoiceRecorderRoomDatabase = VoiceRecorderDatabase(
        ProviderDataBase(context)
    ).build()

    @Singleton
    @Provides
    fun provideVoiceRecorderDao(voiceRecorderRoomDatabase: VoiceRecorderRoomDatabase): VoiceRecorderDao =
        voiceRecorderRoomDatabase.voiceRecorderDao()

    @Singleton
    @Provides
    fun provideVoiceRecorderStorage(voiceRecorderDao: VoiceRecorderDao): VoiceRecorderStorage =
        VoiceRecorderStorage(voiceRecorderDao)

}