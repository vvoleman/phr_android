package cz.vvoleman.phr.common.ui.adapter.grouped

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import cz.vvoleman.phr.common.ui.model.GroupedItemsUiModel
import cz.vvoleman.phr.common_datasource.databinding.ItemGroupedItemsBinding

class GroupedItemsAdapter<TYPE : Any> (private val listener: GroupedItemsAdapterInterface<TYPE>) :
    ListAdapter<GroupedItemsUiModel<TYPE>, GroupedItemsAdapter<TYPE>.SectionViewHolder>(DiffCallback<TYPE>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SectionViewHolder {
        val binding = ItemGroupedItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return SectionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SectionViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class SectionViewHolder(private val binding: ItemGroupedItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: GroupedItemsUiModel<TYPE>) {
            listener.bind(binding, item)
        }
    }

    class DiffCallback<TYPE : Any> : DiffUtil.ItemCallback<GroupedItemsUiModel<TYPE>>() {
        override fun areItemsTheSame(oldItem: GroupedItemsUiModel<TYPE>, newItem: GroupedItemsUiModel<TYPE>) =
            oldItem.value == newItem.value

        override fun areContentsTheSame(oldItem: GroupedItemsUiModel<TYPE>, newItem: GroupedItemsUiModel<TYPE>) =
            oldItem == newItem
    }

    interface GroupedItemsAdapterInterface<TYPE : Any> {
        fun bind(binding: ItemGroupedItemsBinding, item: GroupedItemsUiModel<TYPE>)
    }
}
