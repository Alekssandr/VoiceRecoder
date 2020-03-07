package com.szczecin.voicerecoder.data.database.repo

import androidx.room.Database
import androidx.room.RoomDatabase
import com.szczecin.voicerecoder.data.database.dao.VoiceRecorderDao
import com.szczecin.voicerecoder.data.database.model.VoiceRecorderEntity

@Database(
    entities = [
        VoiceRecorderEntity::class
    ], version = 1, exportSchema = false
)
abstract class VoiceRecorderRoomDatabase : RoomDatabase() {
    abstract fun voiceRecorderDao(): VoiceRecorderDao
}
