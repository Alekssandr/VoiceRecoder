package com.szczecin.voicerecoder.domain.usecase

import com.szczecin.voicerecoder.domain.repo.VoiceRecorderRepository
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class VoiceRecorderStopUseCase @Inject constructor(
    private val voiceRecorderRepository: VoiceRecorderRepository
) {

    fun execute(): BehaviorSubject<Boolean> =
        voiceRecorderRepository.stopRecording()
}