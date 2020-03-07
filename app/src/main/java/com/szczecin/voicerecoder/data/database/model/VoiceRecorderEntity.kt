package com.szczecin.voicerecoder.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "VoiceRecorderEntity")
data class VoiceRecorderEntity(
    @PrimaryKey val id: Long = 0,
    @ColumnInfo val name: String,
    @ColumnInfo val duration: String
)