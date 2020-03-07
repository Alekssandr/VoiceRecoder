package com.szczecin.voicerecoder.data.database.storage

import com.szczecin.voicerecoder.data.database.dao.VoiceRecorderDao
import com.szczecin.voicerecoder.data.database.model.VoiceRecorderEntity
import com.szczecin.voicerecoder.data.mapper.Mapper
import com.szczecin.voicerecoder.domain.model.VoiceRecorder
import io.reactivex.Completable
import io.reactivex.Single

class VoiceRecorderStorage(private val voiceRecorderDao: VoiceRecorderDao) {

    fun insertVoiceRecorder(voiceRecorder: VoiceRecorder): Completable =
        Completable.fromCallable {
            voiceRecorderDao.insert(
                VoiceRecorderEntity(
                    id = voiceRecorder.id,
                    name = voiceRecorder.name,
                    duration = voiceRecorder.duration
                )
            )
        }

    fun getVoiceRecorderList(): Single<List<VoiceRecorder>> =
        voiceRecorderDao.getVoiceRecorderList().map { Mapper().mapFromEntity(it) }

    fun deleteById(id: Int): Completable =
        Completable.fromCallable {
            voiceRecorderDao.deleteByVoiceRecorderId(id)
        }
}