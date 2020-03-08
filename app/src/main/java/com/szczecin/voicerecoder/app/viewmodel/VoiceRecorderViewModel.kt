package com.szczecin.voicerecoder.app.viewmodel

import android.util.Log
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.szczecin.voicerecoder.app.common.rx.RxSchedulers
import com.szczecin.voicerecoder.app.utils.Recording
import com.szczecin.voicerecoder.domain.usecase.VoiceRecorderStartUseCase
import com.szczecin.voicerecoder.domain.usecase.VoiceRecorderStopUseCase
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class VoiceRecorderViewModel @Inject constructor(
    private val schedulers: RxSchedulers,
    private val voiceRecorderStartUseCase: VoiceRecorderStartUseCase,
    private val voiceRecorderStopUseCase: VoiceRecorderStopUseCase
) : ViewModel(), LifecycleObserver {

    private val disposables = CompositeDisposable()
    val recordBtnPermission = MutableLiveData<Boolean>()
    val recordBtn = MutableLiveData<Boolean>()
    val eventOpenRecordings = MutableLiveData<Unit>()
    private val recording = MutableLiveData<Recording>().apply { value = Recording.Permission }

    fun recordButtonClick() {
        checkRecordingStatus()
    }

    private fun checkRecordingStatus() {
        when (recording.value) {
            is Recording.Permission -> recordBtnPermission.value = true
            is Recording.PermissionAccept -> recording()
            is Recording.StartRecording -> stopRecording()
            is Recording.StopRecording -> recording()
        }
    }

    private fun stopRecording() {
        recordBtn.value = false
        disposables += voiceRecorderStopUseCase
            .execute()
            .delay(500, TimeUnit.MILLISECONDS)
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.mainThread())
            .subscribeBy(onComplete = {
                recording.value = Recording.StopRecording
                recordBtn.value = true
            }, onError = {
                Log.e("Recording", "insert onError: ${it.message}")
            })
    }

    private fun recording() {
        recordBtn.value = false
        disposables += voiceRecorderStartUseCase
            .execute()
            .delay(500, TimeUnit.MILLISECONDS)
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.mainThread())
            .subscribeBy(onComplete = {
                recording.value = Recording.StartRecording
                recordBtn.value = true
            }, onError = {
                Log.e("Recording", "insert onError: ${it.message}")
            })
    }

    fun permissionAccepted() {
        recording.value = Recording.PermissionAccept
        checkRecordingStatus()
    }

    fun openRecordings() {
        if (recording.value != Recording.StopRecording) {
            stopRecording()
        }
        eventOpenRecordings.value = Unit
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}