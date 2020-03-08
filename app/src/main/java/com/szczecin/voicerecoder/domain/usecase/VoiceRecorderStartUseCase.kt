package com.szczecin.voicerecoder.domain.usecase

import com.szczecin.voicerecoder.domain.repo.VoiceRecorderRepository
import io.reactivex.Completable
import javax.inject.Inject

class VoiceRecorderStartUseCase @Inject constructor(
    private val voiceRecorderRepository: VoiceRecorderRepository
) {

    fun execute(): Completable =
        voiceRecorderRepository.startRecording()
}