package com.szczecin.voicerecoder.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.szczecin.voicerecoder.data.database.model.VoiceRecorderEntity
import io.reactivex.Single

@Dao
interface VoiceRecorderDao {

    @Query("SELECT * from VoiceRecorderEntity")
    fun getVoiceRecorderList(): Single<List<VoiceRecorderEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(voiceRecorderEntity: VoiceRecorderEntity)

    @Query("DELETE FROM VoiceRecorderEntity WHERE id = :voiceRecorderId")
    fun deleteByVoiceRecorderId(voiceRecorderId: Int)
}