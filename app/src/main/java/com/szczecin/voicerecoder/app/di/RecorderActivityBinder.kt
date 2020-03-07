package com.szczecin.voicerecoder.app.di

import com.szczecin.voicerecoder.app.view.VoiceRecorderActivity
import com.szczecin.voicerecoder.app.di.scopes.PerActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class RecorderActivityBinder {

    @ContributesAndroidInjector(modules = [RecorderActivityModule::class])
    @PerActivity
    abstract fun bindVoiceRecorderActivity(): VoiceRecorderActivity
}