package com.szczecin.voicerecoder.domain.repo

import io.reactivex.Completable
import io.reactivex.subjects.BehaviorSubject

interface VoiceRecorderRepository {
    fun startRecording(): Completable
    fun stopRecording(): BehaviorSubject<Boolean>
}