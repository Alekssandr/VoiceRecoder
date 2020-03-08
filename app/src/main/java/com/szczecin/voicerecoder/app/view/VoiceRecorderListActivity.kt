package com.szczecin.voicerecoder.app.view

import android.graphics.Canvas
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
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

        val recyclerVoiceRecorder = binding.recyclerMovies
        recyclerVoiceRecorder.layoutManager = layoutManager
        recyclerVoiceRecorder.hasFixedSize()
        adapter = ItemsAdapter()
        recyclerVoiceRecorder.adapter = adapter
        recyclerVoiceRecorder.addItemDecoration(
            androidx.recyclerview.widget.DividerItemDecoration(
                this,
                layoutManager.orientation
            ).apply {
                setDrawable(
                    ContextCompat.getDrawable(
                        this@VoiceRecorderListActivity,
                        R.drawable.divider
                    ) as Drawable
                )
            }
        )
        voiceRecorderListViewModel.subscribeForItemClick(adapter.getClickItemObserver())
        voiceRecorderListViewModel.subscribeForRemoveItem(adapter.getClickRemoveItemObserver())

        val itemTouchHelperCallback = setTouchHelper()
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recyclerVoiceRecorder)
    }

    private fun setTouchHelper() =
        object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                viewHolder2: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDirection: Int) {
                adapter.removeItem(viewHolder.adapterPosition, viewHolder)
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                val itemView = viewHolder.itemView
                val colorDrawableBackground = ColorDrawable(
                    ContextCompat.getColor(
                        applicationContext, R.color.colorRemoveItem
                    )
                )
                if (dX > 0) {
                    colorDrawableBackground.setBounds(
                        itemView.left,
                        itemView.top,
                        dX.toInt(),
                        itemView.bottom
                    )
                } else {
                    colorDrawableBackground.setBounds(
                        itemView.right + dX.toInt(),
                        itemView.top,
                        itemView.right,
                        itemView.bottom
                    )
                }

                colorDrawableBackground.draw(c)

                c.save()

                if (dX > 0)
                    c.clipRect(itemView.left, itemView.top, dX.toInt(), itemView.bottom)
                else
                    c.clipRect(
                        itemView.right + dX.toInt(),
                        itemView.top,
                        itemView.right,
                        itemView.bottom
                    )

                c.restore()

                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
            }
        }
}