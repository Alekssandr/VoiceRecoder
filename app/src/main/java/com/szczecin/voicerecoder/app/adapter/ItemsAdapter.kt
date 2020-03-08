package com.szczecin.voicerecoder.app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.szczecin.voicerecoder.R
import com.szczecin.voicerecoder.databinding.RecordingItemBinding
import com.szczecin.voicerecoder.domain.model.VoiceRecorder
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.recording_item.view.*

class ItemsAdapter : RecyclerView.Adapter<ItemsAdapter.ViewHolder>() {
    private val publishSubject = PublishSubject.create<String>()

    private var resultsList: List<VoiceRecorder> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ItemViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return resultsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (holder is ItemViewHolder && resultsList.size > position) {
            holder.bind(resultsList[position])
            holder.itemView.item_layout.setOnClickListener {
                publishSubject.onNext(resultsList[position].name)
            }
        }
    }

    fun update(items: List<VoiceRecorder>) {
        this.resultsList = items
        notifyDataSetChanged()
    }

    fun getClickObserver(): Observable<String> {
        return publishSubject
    }

    abstract class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    class ItemViewHolder(
        private val parent: ViewGroup,
        private val binding: RecordingItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.recording_item,
            parent,
            false
        )
    ) : ViewHolder(binding.root) {
        fun bind(item: VoiceRecorder) {
            binding.name = item.name
            binding.duration = item.duration
        }
    }
}
