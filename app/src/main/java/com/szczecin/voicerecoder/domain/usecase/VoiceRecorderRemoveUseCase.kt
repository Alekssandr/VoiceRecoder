package com.szczecin.voicerecoder.domain.usecase

import com.szczecin.voicerecoder.domain.model.VoiceRecorder
import com.szczecin.voicerecoder.domain.repo.VoiceRecorderListRepository
import io.reactivex.Completable
import javax.inject.Inject

class VoiceRecorderRemoveUseCase @Inject constructor(
    private val voiceRecorderListRepository: VoiceRecorderListRepository
) {
    fun execute(track: VoiceRecorder): Completable =
        voiceRecorderListRepository.removeItem(track)
}