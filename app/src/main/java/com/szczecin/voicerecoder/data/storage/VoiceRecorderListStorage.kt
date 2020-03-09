package com.szczecin.voicerecoder.data.storage

import android.content.Context
import android.media.AudioManager
import android.media.MediaMetadataRetriever
import android.media.MediaPlayer
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import com.szczecin.voicerecoder.domain.model.VoiceRecorder
import io.reactivex.Completable
import io.reactivex.Single
import java.io.File

class VoiceRecorderListStorage(private val context: Context) {

    private val mainPath: String =
        Environment.getExternalStorageDirectory().absolutePath + "/voicerecorder/"
    private val directory: File = File(mainPath)
    private var file: ArrayList<VoiceRecorder>? = null
    private var mediaPlayer: MediaPlayer? = null

    fun initialize(): Completable {
        file = ArrayList()
        getRecording()
        return Completable.complete()
    }

    fun playRecording(title: String): Completable {
        val path = Uri.parse("$mainPath$title.mp3")
        val manager: AudioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        if (manager.isMusicActive) {
            Toast.makeText(
                context,
                "Another recording is just playing! Wait until it's finished!",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            MediaPlayer().apply {
                setAudioStreamType(AudioManager.STREAM_MUSIC)
                setDataSource(context, path)
                prepare()
                start()
            }
        }

        return Completable.complete()
    }

    private fun getRecording() {
        val files: Array<out File>? = directory.listFiles()
        mediaPlayer = MediaPlayer()
        files?.let {
            for (i in files) {
                println(i.name)
                val duration = getLength(i.name)
                file?.add(VoiceRecorder(i.name.removeSuffix(".mp3"), duration))
            }
        }
    }

    private fun getLength(filePath: String): String {

        val mediaMetadataRetriever = MediaMetadataRetriever()
        mediaMetadataRetriever.setDataSource(context, Uri.parse("$mainPath$filePath"))

        val duration =
            mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)

        return convertToTimeFormat(duration)
    }

    private fun convertToTimeFormat(duration: String): String {
        val durationParse = duration.toInt()
        val minutes = durationParse / 1000 / 60
        val seconds = durationParse / 1000 % 60
        return String.format("%d:%02d", minutes, seconds)
    }

    fun removeItem(voiceRecorder: VoiceRecorder): Completable {
        val itemForRemove = File(mainPath + voiceRecorder.name + ".mp3")
        itemForRemove.delete()
        return Completable.complete()
    }

    fun getRecordingsList(): Single<ArrayList<VoiceRecorder>> = Single.just(file)
}