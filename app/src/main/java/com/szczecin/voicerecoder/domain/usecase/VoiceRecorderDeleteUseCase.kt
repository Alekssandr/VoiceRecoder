package com.szczecin.voicerecoder.domain.usecase

import com.szczecin.voicerecoder.domain.repo.VoiceRecorderRepository
import io.reactivex.Completable
import javax.inject.Inject

class VoiceRecorderDeleteUseCase @Inject constructor(
    private val voiceRecorderRepository: VoiceRecorderRepository
) {

    fun execute(voiceRecorderId: Int): Completable =
        voiceRecorderRepository.deleteById(voiceRecorderId)
}