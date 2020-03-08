package com.szczecin.voicerecoder.app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.szczecin.voicerecoder.R
import com.szczecin.voicerecoder.databinding.RecordingItemBinding
import com.szczecin.voicerecoder.domain.model.VoiceRecorder
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.recording_item.view.*

class ItemsAdapter : RecyclerView.Adapter<ItemsAdapter.ViewHolder>() {
    private val publishSubjectItem = PublishSubject.create<String>()
    private val publishSubjectRemovedItem = PublishSubject.create<VoiceRecorder>()

    private var resultsList: MutableList<VoiceRecorder> = mutableListOf()

    private var removedPosition: Int = 0
    lateinit var removedItem: VoiceRecorder

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
                publishSubjectItem.onNext(resultsList[position].name)
            }
        }
    }

    fun update(items: MutableList<VoiceRecorder>) {
        this.resultsList = items
        notifyDataSetChanged()
    }

    fun getClickItemObserver(): Observable<String> {
        return publishSubjectItem
    }

    fun getClickRemoveItemObserver(): Observable<VoiceRecorder> {
        return publishSubjectRemovedItem
    }

    fun removeItem(position: Int, viewHolder: RecyclerView.ViewHolder) {
        removedItem = resultsList[position]
        removedPosition = position

        resultsList.removeAt(position)
        notifyItemRemoved(position)
        var isUndo = false
        Snackbar.make(viewHolder.itemView, "${removedItem.name} removed", Snackbar.LENGTH_LONG)
            .setAction("UNDO") {
                resultsList.add(removedPosition, removedItem)
                notifyItemInserted(removedPosition)
                isUndo = true
            }.addCallback(object : Snackbar.Callback() {
                override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                    super.onDismissed(transientBottomBar, event)
                    if (!isUndo) {
                        publishSubjectRemovedItem.onNext(removedItem)
                    }
                }
            }).show()
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
