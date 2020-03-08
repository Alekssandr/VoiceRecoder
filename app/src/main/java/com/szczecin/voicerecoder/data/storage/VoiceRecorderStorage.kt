package com.szczecin.voicerecoder.data.storage

import android.media.MediaRecorder
import android.os.Environment
import android.util.Log
import io.reactivex.Completable
import java.io.File
import java.io.IOException

class VoiceRecorderStorage : MediaRecorder.OnInfoListener {
    override fun onInfo(mr: MediaRecorder?, what: Int, extra: Int) {
        val a = what
    }

    private var output: String? = null
    private val mainPath: String =
        Environment.getExternalStorageDirectory().absolutePath + "/voicerecorder/"
    private val directory: File = File(mainPath)
    private var state = false
    private var mediaRecorder: MediaRecorder? = null

    fun startRecording(): Completable {
        mediaRecorder = MediaRecorder().apply {
            state = false
            try {
                directory.mkdirs()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            val count = directory.listFiles()?.size ?: 0
            output = "${mainPath}Recording $count.mp3"

            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setOutputFile(output)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)

            try {
                prepare()
                start()
                state = true
            } catch (e: IOException) {
                Log.e("Recording", "prepare() failed")
            }
        }

        return Completable.complete()
    }

    fun stopRecording(): Completable {
        if (state) {
            mediaRecorder?.apply {
                stop()
                release()
            }
            mediaRecorder = null
        }
        return Completable.complete()
    }
}