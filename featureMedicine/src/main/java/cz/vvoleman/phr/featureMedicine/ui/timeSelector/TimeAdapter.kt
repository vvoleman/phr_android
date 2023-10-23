package cz.vvoleman.phr.featureMedicine.ui.timeSelector

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import cz.vvoleman.phr.common.utils.setClearFocusOnDoneAction
import cz.vvoleman.phr.featureMedicine.databinding.ItemTimeSelectorBinding
import java.time.format.DateTimeFormatterBuilder
import java.util.Timer
import java.util.TimerTask

class TimeAdapter(
    private val listener: TimeAdapterListener
) : ListAdapter<TimeUiModel, TimeAdapter.TimeViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeViewHolder {
        val binding =
            ItemTimeSelectorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TimeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TimeViewHolder, position: Int) {
        val item = getItem(position)

        if (item != null) {
            holder.bind(item)
        }
    }

    inner class TimeViewHolder(val binding: ItemTimeSelectorBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                textViewTime.setOnClickListener {
                    val position = bindingAdapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val item = getItem(position)
                        if (item != null) {
                            listener.onTimeClick(position, textViewTime)
                        }
                    }
                }

                editTextQuantity.setOnFocusChangeListener { _, b ->
                    // If focus is lost, change quantity
                    if (!b) {
                        val position = bindingAdapterPosition
                        if (position != RecyclerView.NO_POSITION) {
                            val item = getItem(position)
                            if (item != null) {
                                listener.onQuantityChange(bindingAdapterPosition, editTextQuantity.text.toString().toDouble())
                            }
                        }
                    }
                }

            }
        }

        fun bind(time: TimeUiModel) {
            val formatter = DateTimeFormatterBuilder()
                .appendPattern("HH:mm")
                .toFormatter()
            binding.textViewTime.text = time.time.format(formatter)
            binding.editTextQuantity.setText(time.number.toString())

            binding.editTextQuantity.setClearFocusOnDoneAction()
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<TimeUiModel>() {
        override fun areItemsTheSame(oldItem: TimeUiModel, newItem: TimeUiModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: TimeUiModel, newItem: TimeUiModel): Boolean {
            return oldItem.time == newItem.time
        }
    }

    interface TimeAdapterListener {
        fun onTimeClick(index: Int, anchorView: View)
        fun onQuantityChange(index: Int, newValue: Number)
    }
}
