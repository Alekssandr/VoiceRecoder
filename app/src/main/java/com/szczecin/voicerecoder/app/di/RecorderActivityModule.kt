package com.szczecin.voicerecoder.app.di

import com.szczecin.voicerecoder.app.di.scopes.PerActivity
import com.szczecin.voicerecoder.data.repo.VoiceRecorderDataRepository
import com.szczecin.voicerecoder.data.storage.VoiceRecorderStorage
import com.szczecin.voicerecoder.domain.repo.VoiceRecorderRepository
import dagger.Module
import dagger.Provides

@Module
class RecorderActivityModule {

    @Provides
    @PerActivity
    fun provideVoiceRecorderRepository(voiceRecorderStorage: VoiceRecorderStorage): VoiceRecorderRepository =
        VoiceRecorderDataRepository(voiceRecorderStorage)
}