package com.szczecin.voicerecoder.domain.usecase

import com.szczecin.voicerecoder.domain.model.VoiceRecorder
import com.szczecin.voicerecoder.domain.repo.VoiceRecorderRepository
import io.reactivex.Completable
import javax.inject.Inject

class VoiceRecorderInsertUseCase @Inject constructor(
    private val voiceRecorderRepository: VoiceRecorderRepository
) {

    fun execute(voiceRecorder: VoiceRecorder): Completable =
        voiceRecorderRepository.insert(voiceRecorder)
}