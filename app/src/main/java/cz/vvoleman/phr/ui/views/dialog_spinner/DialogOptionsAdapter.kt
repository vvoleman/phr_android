package cz.vvoleman.phr.ui.views.dialog_spinner

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import cz.vvoleman.phr.data.AdapterPair
import cz.vvoleman.phr.databinding.CustomDialogSpinnerItemBinding

private val TAG = "DialogOptionsAdapter"

class DialogOptionsAdapter (
    private val listener: OnItemClickListener,
) : PagingDataAdapter<AdapterPair, DialogOptionsAdapter.DialogOptionsViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DialogOptionsViewHolder {
        val binding = CustomDialogSpinnerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DialogOptionsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DialogOptionsViewHolder, position: Int) {
        val currentItem = getItem(position)

        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    inner class DialogOptionsViewHolder(private val binding: CustomDialogSpinnerItemBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                radioButton.setOnCheckedChangeListener { _, isChecked ->
                    val position = bindingAdapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val item = getItem(position)
                        if (item != null) {
                            Log.d(TAG, "clicked: $item")
                            val shouldUncheck = listener.onItemClicked(item, position)

                            if (shouldUncheck) {
                                radioButton.isChecked = false
                            }
                        }
                    }
                }
            }
        }

        fun bind(item: AdapterPair) {
            binding.apply {
                radioButton.text = item.stringValue
            }
        }
    }

    interface OnItemClickListener {
        // Returns true for unchecking button
        fun onItemClicked(item: AdapterPair, position: Int): Boolean
    }

    class DiffCallback: DiffUtil.ItemCallback<AdapterPair>() {
        override fun areItemsTheSame(oldItem: AdapterPair, newItem: AdapterPair): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: AdapterPair, newItem: AdapterPair): Boolean {
            return oldItem.id == newItem.id
        }
    }

}