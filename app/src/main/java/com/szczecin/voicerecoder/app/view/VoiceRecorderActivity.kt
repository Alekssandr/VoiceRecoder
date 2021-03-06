package com.szczecin.voicerecoder.app.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.szczecin.voicerecoder.R
import com.szczecin.voicerecoder.app.common.ViewModelFactory
import com.szczecin.voicerecoder.app.common.lifecircle.observeLifecycleIn
import com.szczecin.voicerecoder.app.common.viewModel
import com.szczecin.voicerecoder.app.viewmodel.VoiceRecorderViewModel
import com.szczecin.voicerecoder.databinding.ActivityVoiceRecorderBinding
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_voice_recorder.*
import javax.inject.Inject

class VoiceRecorderActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: ViewModelFactory<VoiceRecorderViewModel>

    private val voiceRecorderViewModel: VoiceRecorderViewModel by viewModel { factory }

    private lateinit var binding: ActivityVoiceRecorderBinding

    private val PERMISSION_ID = 42

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setBinding()
        observeViewModel()
        observeLifecycleIn(voiceRecorderViewModel)
    }

    private fun setBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_voice_recorder)
        binding.voiceRecorderViewModel = voiceRecorderViewModel
    }

    private fun observeViewModel() {
        voiceRecorderViewModel.recordBtnPermission.observe(this, Observer {
            checkPermission()
        })
        voiceRecorderViewModel.recordBtn.observe(this, Observer {
            button.isEnabled = it
            changeColor(it, R.color.colorAccentLight)
        })
        voiceRecorderViewModel.recordStopBtn.observe(this, Observer {
            changeColor(it, R.color.fabBackground)
        })

        voiceRecorderViewModel.eventOpenRecordings.observe(this, Observer {
            if (it) {
                val intent = Intent(this, VoiceRecorderListActivity::class.java)
                startActivity(intent)
            } else {
                Snackbar.make(
                    coordinator_layout,
                    resources.getText(R.string.permissions_record_audio),
                    Snackbar.LENGTH_LONG
                ).show()
            }
        })
    }

    private fun changeColor(it: Boolean, fabBackground: Int) {
        if (it) {
            button.backgroundTintList = ContextCompat.getColorStateList(
                applicationContext, fabBackground
            )
        }
    }

    private fun checkPermission() {
        if (checkPermissions()) {
            record()
        } else {
            requestPermissions()
        }
    }

    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.RECORD_AUDIO
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.RECORD_AUDIO
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ),
            PERMISSION_ID
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_ID) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
//                Handler().postDelayed(Runnable {
//                    record()
//                }, 500)
                record()
            } else {
                Snackbar.make(
                    binding.root,
                    resources.getText(R.string.permissions_info),
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun record() {
        voiceRecorderViewModel.permissionAccepted()
    }
}
