package com.szczecin.voicerecoder.domain.repo

import com.szczecin.voicerecoder.domain.model.VoiceRecorder
import io.reactivex.Completable
import io.reactivex.Single

interface VoiceRecorderRepository {
    fun insert(voiceRecorder: VoiceRecorder): Completable
    fun getAll(): Single<List<VoiceRecorder>>
    fun deleteById(voiceRecorderId: Int): Completable

}