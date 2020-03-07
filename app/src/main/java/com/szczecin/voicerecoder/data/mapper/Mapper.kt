package com.szczecin.voicerecoder.data.mapper

import com.szczecin.voicerecoder.data.database.model.VoiceRecorderEntity
import com.szczecin.voicerecoder.domain.model.VoiceRecorder

class Mapper {
    fun mapFromEntity(voiceRecorderEntityList: List<VoiceRecorderEntity>) =
        mutableListOf<VoiceRecorder>().apply {
            voiceRecorderEntityList.forEach {
                VoiceRecorder().run {
                    id = it.id
                    name = it.name
                    duration = it.duration
                    add(this)
                }
            }
        }
}
