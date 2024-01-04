package cz.vvoleman.phr.common.ui.adapter.problemCategory

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import cz.vvoleman.phr.common.ui.adapter.MarginItemDecoration
import cz.vvoleman.phr.common.ui.model.problemCategory.ProblemCategoryWithInfoUiModel
import cz.vvoleman.phr.common.utils.SizingConstants
import cz.vvoleman.phr.common_datasource.databinding.ItemProblemCategoryBinding

class ProblemCategoryAdapter(
    private val listener: ProblemCategoryListener
) :
    ListAdapter<ProblemCategoryWithInfoUiModel, ProblemCategoryAdapter.ProblemCategoryViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProblemCategoryViewHolder {
        val binding =
            ItemProblemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ProblemCategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProblemCategoryViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class ProblemCategoryViewHolder(private val binding: ItemProblemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.buttonOptions.setOnClickListener {
                if (bindingAdapterPosition != RecyclerView.NO_POSITION) {
                    listener.onOptionsMenuClick(getItem(bindingAdapterPosition), it)
                }
            }

            binding.root.setOnClickListener {
                if (bindingAdapterPosition != RecyclerView.NO_POSITION) {
                    listener.onClick(getItem(bindingAdapterPosition))
                }
            }
        }

        fun bind(item: ProblemCategoryWithInfoUiModel) {
            val color = Color.parseColor(item.problemCategory.color)
            val secondariesAdapter = ProblemCategoryInfoAdapter(color)
            secondariesAdapter.submitList(item.info.secondarySlots)

            binding.apply {
                layoutMainSlot.setBackgroundColor(color)
                textViewMainNumber.text = item.info.mainSlot.first.toString()
                textViewMainText.text = item.info.mainSlot.second

                textViewTitle.text = item.problemCategory.name
            }

            binding.recyclerView.apply {
                adapter = secondariesAdapter
                layoutManager = androidx.recyclerview.widget.LinearLayoutManager(binding.root.context)
                addItemDecoration(MarginItemDecoration(SizingConstants.MARGIN_SIZE))
                setHasFixedSize(true)
            }
        }

    }

    private class DiffCallback : DiffUtil.ItemCallback<ProblemCategoryWithInfoUiModel>() {
        override fun areItemsTheSame(oldItem: ProblemCategoryWithInfoUiModel, newItem: ProblemCategoryWithInfoUiModel): Boolean {
            return oldItem.problemCategory.id == newItem.problemCategory.id
        }

        override fun areContentsTheSame(oldItem: ProblemCategoryWithInfoUiModel, newItem: ProblemCategoryWithInfoUiModel): Boolean {
            return oldItem == newItem
        }

    }

    interface ProblemCategoryListener {
        fun onClick(problemCategory: ProblemCategoryWithInfoUiModel)
        fun onOptionsMenuClick(problemCategory: ProblemCategoryWithInfoUiModel, anchorView: View)
    }

}
