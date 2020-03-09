package com.szczecin.voicerecoder.data.storage

import android.media.MediaRecorder
import android.os.Environment
import android.os.FileObserver
import android.util.Log
import io.reactivex.Completable
import io.reactivex.subjects.BehaviorSubject
import java.io.File
import java.io.IOException

class VoiceRecorderStorage : FileWasWrittenListener {

    private var recordingFinishEvent = BehaviorSubject.create<Boolean>()

    private var output: String? = null
    private val mainPath: String =
        Environment.getExternalStorageDirectory().absolutePath + "/voicerecorder/"
    private val directory: File = File(mainPath)
    private var state = false
    private var mediaRecorder: MediaRecorder? = null
    var fb: FileWasWrittenObserver = FileWasWrittenObserver(
        mainPath,
        FileObserver.CLOSE_WRITE
    )

    init {
        fb.addListener(this)
    }

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
        fb.startWatching()
        return Completable.complete()
    }

    fun stopRecording(): BehaviorSubject<Boolean> {
        if (state) {
            mediaRecorder?.apply {
                stop()
                release()
            }
            mediaRecorder = null
        }

        return recordingFinishEvent
    }

    override fun isFinished() {
        fb.stopWatching()
        recordingFinishEvent.onNext(true)
    }
}

class FileWasWrittenObserver(path: String, mask: Int) : FileObserver(path, mask) {
    lateinit var fileWasWrittenListener: FileWasWrittenListener

    override fun onEvent(event: Int, path: String?) {
        fileWasWrittenListener.isFinished()
    }

    fun addListener(fileWasWrittenListener: FileWasWrittenListener) {
        this.fileWasWrittenListener = fileWasWrittenListener
    }
}

interface FileWasWrittenListener {
    fun isFinished()
}