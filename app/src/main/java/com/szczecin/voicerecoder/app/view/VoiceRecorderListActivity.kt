package com.szczecin.voicerecoder.app.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.szczecin.voicerecoder.R
import com.szczecin.voicerecoder.app.adapter.ItemsAdapter
import com.szczecin.voicerecoder.app.common.ViewModelFactory
import com.szczecin.voicerecoder.app.common.lifecircle.observeLifecycleIn
import com.szczecin.voicerecoder.app.common.viewModel
import com.szczecin.voicerecoder.app.viewmodel.VoiceRecorderListViewModel
import com.szczecin.voicerecoder.databinding.ActivityVoiceRecorderListBinding
import dagger.android.AndroidInjection
import javax.inject.Inject

class VoiceRecorderListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVoiceRecorderListBinding

    @Inject
    lateinit var factory: ViewModelFactory<VoiceRecorderListViewModel>

    private val voiceRecorderListViewModel: VoiceRecorderListViewModel by viewModel { factory }
    private lateinit var adapter: ItemsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_voice_recorder_list)
        observeLifecycleIn(voiceRecorderListViewModel)
        setBinding()
    }

    private fun setBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_voice_recorder_list)
        binding.run {
            binding.viewModel = voiceRecorderListViewModel
            initRecycler()
            lifecycleOwner = this@VoiceRecorderListActivity
        }
    }

    private fun initRecycler() {
        val layoutManager = androidx.recyclerview.widget.GridLayoutManager(this, 1)

        val recyclerMovies = binding.recyclerMovies
        recyclerMovies.layoutManager = layoutManager
        recyclerMovies.hasFixedSize()
        adapter = ItemsAdapter()
        recyclerMovies.adapter = adapter
        recyclerMovies.addItemDecoration(
            androidx.recyclerview.widget.DividerItemDecoration(
                this,
                layoutManager.orientation
            )
        )
        voiceRecorderListViewModel.subscribeForItemClick(adapter.getClickObserver())
    }
}