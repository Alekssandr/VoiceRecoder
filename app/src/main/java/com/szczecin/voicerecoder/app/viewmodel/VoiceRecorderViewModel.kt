package com.szczecin.voicerecoder.app.viewmodel

import android.util.Log
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import com.szczecin.voicerecoder.app.common.rx.RxSchedulers
import com.szczecin.voicerecoder.domain.model.VoiceRecorder
import com.szczecin.voicerecoder.domain.usecase.VoiceRecorderGetListUseCase
import com.szczecin.voicerecoder.domain.usecase.VoiceRecorderInsertUseCase
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class VoiceRecorderViewModel @Inject constructor(
    private val schedulers: RxSchedulers,
    private val voiceRecorderInsertUseCase: VoiceRecorderInsertUseCase,
    private val voiceRecorderGetListUseCase: VoiceRecorderGetListUseCase
) : ViewModel(), LifecycleObserver {

    private val disposables = CompositeDisposable()

    fun record() {
        insert(VoiceRecorder(1, "test2", "3"))
    }

    private fun insert(voiceRecorder: VoiceRecorder) {
        disposables += voiceRecorderInsertUseCase
            .execute(voiceRecorder)
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.mainThread())
            .subscribeBy(onComplete = {
                Log.d("Record", "insert onComplete")
            }, onError = {
                Log.e("Record", "insert onError: ${it.message}")
            })
    }

    // TODO change body of func later
    fun openRecordings() {
        disposables += voiceRecorderGetListUseCase
            .execute()
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.mainThread())
            .subscribeBy(onSuccess = {
                Log.d("Record", "openRecordings list $it")
            }, onError = {
                Log.e("Record", "openRecordings onError: ${it.message}")
            })
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}