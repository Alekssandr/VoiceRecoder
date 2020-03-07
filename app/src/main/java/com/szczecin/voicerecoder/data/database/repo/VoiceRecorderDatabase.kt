package com.szczecin.voicerecoder.data.database.repo

class VoiceRecorderDatabase(private val providerDataBase: ProviderDataBase) {
    fun build(): VoiceRecorderRoomDatabase = providerDataBase.createBuilder().build()
}