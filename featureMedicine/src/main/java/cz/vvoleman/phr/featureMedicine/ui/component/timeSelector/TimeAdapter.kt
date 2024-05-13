package cz.vvoleman.phr.featureMedicine.ui.component.timeSelector

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import cz.vvoleman.phr.common.utils.TimeConstants
import cz.vvoleman.phr.common.utils.setClearFocusOnDoneAction
import cz.vvoleman.phr.common.utils.textChanges
import cz.vvoleman.phr.featureMedicine.databinding.ItemTimeSelectorBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.time.format.DateTimeFormatterBuilder

class TimeAdapter(
    private val lifecycleScope: LifecycleCoroutineScope,
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

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
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

                editTextQuantity.textChanges()
                    .debounce(TimeConstants.DEBOUNCE_TIME)
                    .onEach {
                        val position = bindingAdapterPosition
                        if (position != RecyclerView.NO_POSITION) {
                            val item = getItem(position)
                            if (item != null) {
                                val newValue = if (it.isNullOrBlank()) {
                                    0
                                } else {
                                    it.toString().toFloat()
                                }

                                listener.onQuantityChange(
                                    bindingAdapterPosition,
                                    newValue
                                )
                            }
                        }
                    }
                    .launchIn(lifecycleScope)
            }
        }

        fun bind(time: TimeUiModel) {
            val formatter = DateTimeFormatterBuilder()
                .appendPattern("HH:mm")
                .toFormatter()
            binding.textViewTime.text = time.time.format(formatter)

            if (time.number == 0) {
                binding.editTextQuantity.setText("")
            } else {
                // Has number decimal part?
                if (time.number.toFloat() % 1 == 0.0f) {
                    binding.editTextQuantity.setText(time.number.toInt().toString())
                } else {
                    binding.editTextQuantity.setText(time.number.toString())
                }
//                binding.editTextQuantity.setText(time.number.toString())
            }

            binding.editTextQuantity.setClearFocusOnDoneAction()

            // If item is even (0,2,4,6,8,...) set background color to light gray
            if (bindingAdapterPosition % 2 == 0) {
                binding.root.setBackgroundColor(Color.parseColor("#f3f3f3"))
            } else {
                binding.root.setBackgroundColor(binding.root.resources.getColor(android.R.color.white, null))
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<TimeUiModel>() {
        override fun areItemsTheSame(oldItem: TimeUiModel, newItem: TimeUiModel): Boolean {
            return oldItem.time == newItem.time
        }

        override fun areContentsTheSame(oldItem: TimeUiModel, newItem: TimeUiModel): Boolean {
            return oldItem == newItem
        }
    }

    interface TimeAdapterListener {
        fun onTimeClick(index: Int, anchorView: View)
        fun onQuantityChange(index: Int, newValue: Number)
    }
}
