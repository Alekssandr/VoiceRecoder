package com.szczecin.voicerecoder.domain.usecase

import com.szczecin.voicerecoder.domain.model.VoiceRecorder
import com.szczecin.voicerecoder.domain.repo.VoiceRecorderRepository
import io.reactivex.Single
import javax.inject.Inject

class VoiceRecorderGetListUseCase @Inject constructor(
    private val voiceRecorderRepository: VoiceRecorderRepository
) {

    fun execute(): Single<List<VoiceRecorder>> =
        voiceRecorderRepository.getAll()
}