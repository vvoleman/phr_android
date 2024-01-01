package cz.vvoleman.phr.common.ui.adapter.healthcare

import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import cz.vvoleman.phr.common.ui.model.healthcare.core.AdditionalInfoUiModel
import cz.vvoleman.phr.common_datasource.databinding.ItemAdditionalInfoBinding

class AdditionalInfoAdapter<T : Parcelable>(
    private val listener: AdditionalInfoAdapterListener<T>
) :
    ListAdapter<AdditionalInfoUiModel<T>, AdditionalInfoAdapter<T>.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemAdditionalInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    inner class ViewHolder(private val binding: ItemAdditionalInfoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: AdditionalInfoUiModel<T>) {
            binding.root.setOnClickListener {
                item.onClick?.invoke(listener.onAdditionalInfoClick())
            }
            item.icon?.let { binding.textViewInfo.setCompoundDrawablesWithIntrinsicBounds(it, 0, 0, 0) }
            binding.textViewInfo.text = item.text
        }
    }

    private class DiffCallback<T : Parcelable> : DiffUtil.ItemCallback<AdditionalInfoUiModel<T>>() {
        override fun areItemsTheSame(oldItem: AdditionalInfoUiModel<T>, newItem: AdditionalInfoUiModel<T>): Boolean {
            return oldItem.text == newItem.text
        }

        override fun areContentsTheSame(oldItem: AdditionalInfoUiModel<T>, newItem: AdditionalInfoUiModel<T>): Boolean {
            return oldItem == newItem
        }
    }

    interface AdditionalInfoAdapterListener<T : Parcelable> {
        fun onAdditionalInfoClick(): T
    }

}
