package cz.vvoleman.phr.common.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ListAdapter
import cz.vvoleman.phr.common.ui.model.GroupedItemsUiModel

class GroupedItemsAdapter {
//    ListAdapter<GroupedItemsUiModel, GroupedItemsAdapter.SectionViewHolder>(DiffCallback()) {
//
//        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SectionViewHolder {
//            val binding = ItemGroupedItems.inflate(LayoutInflater.from(parent.context), parent, false)
//
//            return SectionViewHolder(binding)
//        }
//
//        override fun onBindViewHolder(holder: SectionViewHolder, position: Int) {
//            val currentItem = getItem(position)
//            holder.bind(currentItem)
//        }
//
//        inner class SectionViewHolder(private val binding: ItemSectionBinding) :
//            RecyclerView.ViewHolder(binding.root) {
//
//            fun bind(item: Section) {
//                binding.apply {
//                    textViewTitle.text = item.name
//                    val recordAdapter = MedicalRecordAdapter(listener)
//                    recordAdapter.submitList(item.items)
//                    recyclerView.apply {
//                        adapter = recordAdapter
//                        layoutManager = LinearLayoutManager(context)
//                    }
//                }
//            }
//        }
//
//        class DiffCallback : DiffUtil.ItemCallback<Section>() {
//            override fun areItemsTheSame(oldItem: Section, newItem: Section) =
//                oldItem.name == newItem.name
//
//            override fun areContentsTheSame(oldItem: Section, newItem: Section) =
//                oldItem == newItem
//        }
}