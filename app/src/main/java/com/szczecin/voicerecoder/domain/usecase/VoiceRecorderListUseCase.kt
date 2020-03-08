package com.szczecin.voicerecoder.domain.usecase

import com.szczecin.voicerecoder.domain.model.VoiceRecorder
import com.szczecin.voicerecoder.domain.repo.VoiceRecorderListRepository
import io.reactivex.Single
import javax.inject.Inject

class VoiceRecorderListUseCase @Inject constructor(
    private val voiceRecorderListRepository: VoiceRecorderListRepository
) {

    fun execute(): Single<ArrayList<VoiceRecorder>> =
            voiceRecorderListRepository.getListRecording()
}