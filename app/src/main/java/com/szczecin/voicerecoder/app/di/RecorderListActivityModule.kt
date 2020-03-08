package com.szczecin.voicerecoder.app.di

import com.szczecin.voicerecoder.app.di.scopes.PerActivity
import com.szczecin.voicerecoder.data.repo.VoiceRecorderListDataRepository
import com.szczecin.voicerecoder.data.storage.VoiceRecorderListStorage
import com.szczecin.voicerecoder.domain.repo.VoiceRecorderListRepository
import dagger.Module
import dagger.Provides

@Module
class RecorderListActivityModule {
    @Provides
    @PerActivity
    fun provideVoiceRecorderListRepository(voiceRecorderStorage: VoiceRecorderListStorage): VoiceRecorderListRepository =
        VoiceRecorderListDataRepository(voiceRecorderStorage)
}