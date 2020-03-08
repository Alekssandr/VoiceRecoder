package com.szczecin.voicerecoder.domain.repo

import io.reactivex.Completable

interface VoiceRecorderRepository {
    fun startRecording(): Completable
    fun stopRecording(): Completable
}