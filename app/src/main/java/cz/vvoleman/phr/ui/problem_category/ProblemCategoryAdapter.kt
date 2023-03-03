package cz.vvoleman.phr.ui.problem_category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import cz.vvoleman.phr.data.core.medical_record.ProblemCategory
import cz.vvoleman.phr.databinding.ItemProblemCategoryBinding

class ProblemCategoryAdapter : ListAdapter<ProblemCategory, ProblemCategoryAdapter.ProblemCategoryViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProblemCategoryViewHolder {
        val binding = ItemProblemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProblemCategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProblemCategoryViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class ProblemCategoryViewHolder(private val binding: ItemProblemCategoryBinding) : ViewHolder(binding.root) {

        init {
            binding.apply {  }
        }

        fun bind(item: ProblemCategory) {
            binding.apply {
                countTextView.text = item..toString()
            }
        }

    }

    class DiffCallback : DiffUtil.ItemCallback<ProblemCategory>() {

        override fun areItemsTheSame(oldItem: ProblemCategory, newItem: ProblemCategory): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ProblemCategory, newItem: ProblemCategory): Boolean {
            return oldItem == newItem
        }

    }

}