package cz.vvoleman.phr.common.ui.adapter.filter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import cz.vvoleman.phr.common.ui.model.FilterPair
import cz.vvoleman.phr.common_datasource.databinding.ItemFilterBinding

class FilterAdapter(
    private val listener: FilterAdapterListener
) :
    ListAdapter<FilterPair, FilterAdapter.FilterViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterViewHolder {
        val binding = ItemFilterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FilterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class FilterViewHolder(private val binding: ItemFilterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: FilterPair) {
            binding.apply {
                checkBox.isChecked = item.checked
                textView.text = item.stringValue

                checkBox.setOnCheckedChangeListener { _, isChecked ->
                    changeItemChecked(item, isChecked)
                }
                textView.setOnClickListener {
                    changeItemChecked(item, !item.checked)
                }
            }
        }

        private fun changeItemChecked(item: FilterPair, isChecked: Boolean) {
            item.checked = isChecked
            binding.checkBox.isChecked = isChecked
            listener.onOptionCheckChanged(item)
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<FilterPair>() {
        override fun areItemsTheSame(oldItem: FilterPair, newItem: FilterPair): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: FilterPair, newItem: FilterPair): Boolean {
            return oldItem == newItem
        }
    }

    interface FilterAdapterListener {
        fun onOptionCheckChanged(item: FilterPair)
    }
}
