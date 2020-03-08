package com.szczecin.voicerecoder.data.repo

import com.szczecin.voicerecoder.data.storage.VoiceRecorderStorage
import com.szczecin.voicerecoder.domain.repo.VoiceRecorderRepository

class VoiceRecorderDataRepository(
    private val voiceRecorderStorage: VoiceRecorderStorage
) : VoiceRecorderRepository {
    override fun startRecording() = voiceRecorderStorage.startRecording()
    override fun stopRecording() = voiceRecorderStorage.stopRecording()
}