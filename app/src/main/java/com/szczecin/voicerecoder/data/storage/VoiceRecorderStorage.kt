package com.szczecin.voicerecoder.data.storage

import android.media.MediaRecorder
import android.os.Environment
import android.util.Log
import androidx.lifecycle.MutableLiveData
import io.reactivex.Completable
import java.io.File
import java.io.IOException

class VoiceRecorderStorage {

    private var output: String? = null
    private val mainPath: String =
        Environment.getExternalStorageDirectory().absolutePath + "/voicerecorder/"
    private val directory: File = File(mainPath)
    private val recordingTimeString = MutableLiveData<String>()
    private var state = false
    private var mediaRecorder: MediaRecorder? = null

    private fun setMediaRecorderAttributes() {
        Log.d("Recording", "setMediaRecorderAttributes start")
        mediaRecorder = MediaRecorder()
        mediaRecorder?.setAudioSource(MediaRecorder.AudioSource.MIC)
        mediaRecorder?.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
        mediaRecorder?.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
        mediaRecorder?.setOutputFile(output)
        Log.d("Recording", "setMediaRecorderAttributes finish")
    }

    fun startRecording(): Completable {
        Log.d("Recording", "initialDirectory start")
        try {
            directory.mkdirs()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        if (directory.exists()) {
            val count = directory.listFiles()?.size ?: 0
            output = "$mainPath$count.mp3"
        }

        setMediaRecorderAttributes()
        Log.d("Recording", "initialDirectory finsih")
        try {
            Log.d("Recording", "initialDirectory finsih")
            mediaRecorder?.prepare()
            mediaRecorder?.start()
            state = true
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return Completable.fromCallable { state }
    }

    fun stopRecording(): Completable {
        Log.d("Recording", "stopRecording")
        if (state) {
            Log.d(
                "Recording",
                "stopRecording inside recordingTimeString: ${recordingTimeString.value}"
            )
            mediaRecorder?.stop()
            mediaRecorder?.release()
            state = false
            setMediaRecorderAttributes()
            Log.d(
                "Recording",
                "stopRecording inside after recordingTimeString: ${recordingTimeString.value}"
            )

            return Completable.fromCallable { true }
        }
        return Completable.fromCallable { false }
    }
}