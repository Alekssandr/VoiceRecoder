package com.szczecin.voicerecoder.app.di

import com.szczecin.voicerecoder.app.view.VoiceRecorderActivity
import com.szczecin.voicerecoder.app.di.scopes.PerActivity
import com.szczecin.voicerecoder.app.view.VoiceRecorderListActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class RecorderActivityBinder {

    @ContributesAndroidInjector(modules = [RecorderActivityModule::class])
    @PerActivity
    abstract fun bindVoiceRecorderActivity(): VoiceRecorderActivity

    @ContributesAndroidInjector(modules = [RecorderListActivityModule::class])
    @PerActivity
    abstract fun bindVoiceRecorderListActivity(): VoiceRecorderListActivity
}