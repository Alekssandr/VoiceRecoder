package com.szczecin.voicerecoder.data.database.repo

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

const val DATA_BASE_NAME = "voice_recorder"

class ProviderDataBase(private val appContext: Context) {
    fun createBuilder(): RoomDatabase.Builder<VoiceRecorderRoomDatabase> =
        Room.databaseBuilder(appContext, VoiceRecorderRoomDatabase::class.java, DATA_BASE_NAME)
}