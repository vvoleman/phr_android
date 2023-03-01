package cz.vvoleman.phr.ui.medical_records

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import cz.vvoleman.phr.databinding.ItemSectionBinding

class SectionAdapter(private val listener: MedicalRecordAdapter.OnItemClickListener) :
    ListAdapter<Section, SectionAdapter.SectionViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SectionViewHolder {
        val binding = ItemSectionBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return SectionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SectionViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class SectionViewHolder(private val binding: ItemSectionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Section) {
            binding.apply {
                textViewTitle.text = item.name
                val recordAdapter = MedicalRecordAdapter(listener)
                recordAdapter.submitList(item.items)
                Log.d("SectionAdapter", "bind: ${item.items}")
                recyclerView.apply {
                    adapter = recordAdapter
                    layoutManager = LinearLayoutManager(context)
                }
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Section>() {
        override fun areItemsTheSame(oldItem: Section, newItem: Section) =
            oldItem.name == newItem.name

        override fun areContentsTheSame(oldItem: Section, newItem: Section) =
            oldItem == newItem
    }
}