package com.szczecin.voicerecoder.data.repo

import com.szczecin.voicerecoder.data.storage.VoiceRecorderStorage
import com.szczecin.voicerecoder.domain.repo.VoiceRecorderRepository

class VoiceRecorderDataRepository(
    private val voiceRecorderStorage: VoiceRecorderStorage
) : VoiceRecorderRepository {
//    override fun getListSize(): Single<Int> =
//        voiceRecorderStorage.getVoiceRecorderListSize()

    override fun startRecording() = voiceRecorderStorage.startRecording()
    override fun stopRecording() = voiceRecorderStorage.stopRecording()

//    override fun getAll(): Single<List<VoiceRecorder>> =
//        voiceRecorderStorage.getVoiceRecorderList()
//
//    override fun deleteById(voiceRecorderId: Int): Completable =
//        voiceRecorderStorage.deleteById(voiceRecorderId)
}