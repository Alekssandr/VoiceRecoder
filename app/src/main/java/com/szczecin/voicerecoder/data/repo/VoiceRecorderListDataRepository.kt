package com.szczecin.voicerecoder.data.repo

import com.szczecin.voicerecoder.data.storage.VoiceRecorderListStorage
import com.szczecin.voicerecoder.domain.model.VoiceRecorder
import com.szczecin.voicerecoder.domain.repo.VoiceRecorderListRepository
import io.reactivex.Completable
import io.reactivex.Single

class VoiceRecorderListDataRepository(
    private val voiceRecorderListStorage: VoiceRecorderListStorage
) : VoiceRecorderListRepository {
    override fun getListRecording(): Single<ArrayList<VoiceRecorder>> =
        voiceRecorderListStorage.initialize().andThen(voiceRecorderListStorage.getRecordingsList())

    override fun playRecording(track: String): Completable =
        voiceRecorderListStorage.playRecording(track)

    override fun initialize(): Completable = voiceRecorderListStorage.initialize()
}