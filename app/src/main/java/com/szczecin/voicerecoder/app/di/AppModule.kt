package com.szczecin.voicerecoder.app.di

import android.app.Application
import android.content.Context
import android.media.MediaRecorder
import com.szczecin.voicerecoder.app.common.rx.RxSchedulers
import com.szczecin.voicerecoder.data.storage.VoiceRecorderListStorage
import com.szczecin.voicerecoder.data.storage.VoiceRecorderStorage
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
    fun provideMediaRecorder(): MediaRecorder = MediaRecorder()

    @Singleton
    @Provides
    fun provideVoiceRecorderStorage(): VoiceRecorderStorage =
        VoiceRecorderStorage()

    @Singleton
    @Provides
    fun provideVoiceRecorderListStorage(context: Context): VoiceRecorderListStorage =
        VoiceRecorderListStorage(context)
}