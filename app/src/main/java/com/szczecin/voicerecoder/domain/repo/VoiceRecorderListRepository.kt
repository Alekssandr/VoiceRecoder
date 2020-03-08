package com.szczecin.voicerecoder.domain.repo

import com.szczecin.voicerecoder.domain.model.VoiceRecorder
import io.reactivex.Completable
import io.reactivex.Single

interface VoiceRecorderListRepository {
    fun getListRecording(): Single<ArrayList<VoiceRecorder>>
    fun playRecording(track: String): Completable
    fun initialize(): Completable
}