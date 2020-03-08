package com.szczecin.voicerecoder.app.adapter

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.szczecin.voicerecoder.domain.model.VoiceRecorder

@BindingAdapter("resultsList")
fun RecyclerView.bindItems(items: List<VoiceRecorder>?) {

    items?.let { val adapter = adapter as ItemsAdapter
        adapter.update(items) }
}