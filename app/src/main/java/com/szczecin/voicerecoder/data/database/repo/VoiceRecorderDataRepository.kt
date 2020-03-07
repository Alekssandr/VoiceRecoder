package com.szczecin.voicerecoder.data.database.repo

import com.szczecin.voicerecoder.data.database.storage.VoiceRecorderStorage
import com.szczecin.voicerecoder.domain.model.VoiceRecorder
import com.szczecin.voicerecoder.domain.repo.VoiceRecorderRepository
import io.reactivex.Completable
import io.reactivex.Single

class VoiceRecorderDataRepository(
    private val voiceRecorderStorage: VoiceRecorderStorage
) : VoiceRecorderRepository {
    override fun insert(voiceRecorder: VoiceRecorder): Completable =
        voiceRecorderStorage.insertVoiceRecorder(voiceRecorder)

    override fun getAll(): Single<List<VoiceRecorder>> =
        voiceRecorderStorage.getVoiceRecorderList()

    override fun deleteById(voiceRecorderId: Int): Completable =
        voiceRecorderStorage.deleteById(voiceRecorderId)
}