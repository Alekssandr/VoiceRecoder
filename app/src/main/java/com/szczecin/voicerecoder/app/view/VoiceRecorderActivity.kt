package com.szczecin.voicerecoder.app.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.szczecin.voicerecoder.app.common.lifecircle.observeLifecycleIn
import com.szczecin.voicerecoder.R
import com.szczecin.voicerecoder.app.common.ViewModelFactory
import com.szczecin.voicerecoder.app.common.viewModel
import com.szczecin.voicerecoder.app.viewmodel.VoiceRecorderViewModel
import com.szczecin.voicerecoder.databinding.ActivityVoiceRecorderBinding
import dagger.android.AndroidInjection
import javax.inject.Inject

class VoiceRecorderActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: ViewModelFactory<VoiceRecorderViewModel>

    private val voiceRecorderViewModel: VoiceRecorderViewModel by viewModel { factory }

    private lateinit var binding: ActivityVoiceRecorderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setBinding()
        observeLifecycleIn(voiceRecorderViewModel)
    }

    private fun setBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_voice_recorder)
        binding.voiceRecorderViewModel = voiceRecorderViewModel
    }
}
