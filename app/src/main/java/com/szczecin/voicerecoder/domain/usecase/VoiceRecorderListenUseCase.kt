package com.szczecin.voicerecoder.domain.usecase

import com.szczecin.voicerecoder.domain.repo.VoiceRecorderListRepository
import io.reactivex.Completable
import javax.inject.Inject

class VoiceRecorderListenUseCase @Inject constructor(
    private val voiceRecorderListRepository: VoiceRecorderListRepository
) {
    fun execute(track: String): Completable =
        voiceRecorderListRepository.playRecording(track)
}