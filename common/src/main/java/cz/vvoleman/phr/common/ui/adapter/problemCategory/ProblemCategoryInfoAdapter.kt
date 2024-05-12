package cz.vvoleman.phr.common.ui.adapter.problemCategory

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import cz.vvoleman.phr.common.ui.model.healthcare.core.AdditionalInfoUiModel
import cz.vvoleman.phr.common.ui.model.problemCategory.ProblemCategoryUiModel
import cz.vvoleman.phr.common_datasource.databinding.ItemProblemCategoryInfoBinding

class ProblemCategoryInfoAdapter(
    private val color: Int
) : ListAdapter<
    AdditionalInfoUiModel<ProblemCategoryUiModel>,
    ProblemCategoryInfoAdapter.ProblemCategoryInfoViewHolder
    >(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProblemCategoryInfoViewHolder {
        val binding =
            ItemProblemCategoryInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ProblemCategoryInfoViewHolder(binding, color)
    }

    override fun onBindViewHolder(holder: ProblemCategoryInfoViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class ProblemCategoryInfoViewHolder(
        private val binding: ItemProblemCategoryInfoBinding,
        private val color: Int
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: AdditionalInfoUiModel<ProblemCategoryUiModel>) {
            binding.apply {
                chip.chipBackgroundColor = ColorStateList.valueOf(color)
                chip.text = item.text

                if (item.icon != null) {
                    chip.chipIcon = AppCompatResources.getDrawable(binding.root.context, item.icon)
                }
            }
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<AdditionalInfoUiModel<ProblemCategoryUiModel>>() {
        override fun areItemsTheSame(
            oldItem: AdditionalInfoUiModel<ProblemCategoryUiModel>,
            newItem: AdditionalInfoUiModel<ProblemCategoryUiModel>
        ): Boolean {
            return oldItem.text == newItem.text && oldItem.icon == newItem.icon
        }

        override fun areContentsTheSame(
            oldItem: AdditionalInfoUiModel<ProblemCategoryUiModel>,
            newItem: AdditionalInfoUiModel<ProblemCategoryUiModel>
        ): Boolean {
            return oldItem == newItem
        }
    }
}
