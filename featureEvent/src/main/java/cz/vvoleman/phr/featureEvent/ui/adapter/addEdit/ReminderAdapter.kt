package cz.vvoleman.phr.featureEvent.ui.adapter.addEdit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import cz.vvoleman.phr.featureEvent.databinding.ItemReminderBinding
import cz.vvoleman.phr.featureEvent.ui.model.addEdit.ReminderUiModel

class ReminderAdapter(
    private val listener: ReminderListener
) : ListAdapter<ReminderUiModel, ReminderAdapter.ReminderViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReminderViewHolder {
        val binding = ItemReminderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReminderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReminderViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class ReminderViewHolder(private val binding: ItemReminderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            val onClickListener: (View) -> Unit = {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    listener.onReminderClick(item)
                }
            }

            binding.root.setOnClickListener(onClickListener)
            binding.checkBox.setOnClickListener(onClickListener)
        }

        fun bind(item: ReminderUiModel) {
            binding.textView.text = binding.root.context.getString(item.id)
            binding.checkBox.isChecked = item.isEnabled
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<ReminderUiModel>() {
        override fun areItemsTheSame(oldItem: ReminderUiModel, newItem: ReminderUiModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ReminderUiModel, newItem: ReminderUiModel): Boolean {
            return oldItem == newItem
        }
    }

    interface ReminderListener {
        fun onReminderClick(reminder: ReminderUiModel)
    }
}
