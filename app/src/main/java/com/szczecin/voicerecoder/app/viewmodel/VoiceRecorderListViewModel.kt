package com.szczecin.voicerecoder.app.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.Lifecycle
import com.szczecin.voicerecoder.app.common.rx.RxSchedulers
import com.szczecin.voicerecoder.domain.model.VoiceRecorder
import com.szczecin.voicerecoder.domain.usecase.VoiceRecorderListUseCase
import com.szczecin.voicerecoder.domain.usecase.VoiceRecorderListenUseCase
import com.szczecin.voicerecoder.domain.usecase.VoiceRecorderRemoveUseCase
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class VoiceRecorderListViewModel @Inject constructor(
    private val schedulers: RxSchedulers,
    private val voiceRecorderStartUseCase: VoiceRecorderListUseCase,
    private val voiceRecorderListenUseCase: VoiceRecorderListenUseCase,
    private val voiceRecorderRemoveUseCase: VoiceRecorderRemoveUseCase
) : ViewModel(), LifecycleObserver {

    private val disposables = CompositeDisposable()
    private val viewSubscriptions = CompositeDisposable()
    val resultsList: MutableLiveData<List<VoiceRecorder>> = MutableLiveData()

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    private fun getRecords() {
        disposables += voiceRecorderStartUseCase
            .execute()
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.mainThread())
            .subscribeBy(onSuccess = {
                resultsList.value = it
            }, onError = {
                Log.e("Recording", "insert onError: ${it.message}")
            })
    }

    fun subscribeForItemClick(clickObserver: Observable<String>) {
        viewSubscriptions.add(
            clickObserver
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.mainThread())
                .subscribe { listenTrack(it) })
    }

    fun subscribeForRemoveItem(clickObserver: Observable<VoiceRecorder>) {
        viewSubscriptions.add(
            clickObserver
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.mainThread())
                .subscribe { removeTrack(it) })
    }

    private fun removeTrack(track: VoiceRecorder) {
        disposables += voiceRecorderRemoveUseCase
            .execute(track)
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.mainThread())
            .subscribeBy(onComplete = {
            }, onError = {
                Log.e("Recording", "insert onError: ${it.message}")
            })
    }

    private fun listenTrack(track: String) {
        disposables += voiceRecorderListenUseCase
            .execute(track)
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.mainThread())
            .subscribeBy(onComplete = {
            }, onError = {
                Log.e("Recording", "insert onError: ${it.message}")
            })
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}