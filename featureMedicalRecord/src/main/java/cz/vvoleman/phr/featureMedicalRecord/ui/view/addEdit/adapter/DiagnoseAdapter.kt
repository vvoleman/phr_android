package cz.vvoleman.phr.featureMedicalRecord.ui.view.addEdit.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import cz.vvoleman.phr.featureMedicalRecord.databinding.ItemDiagnoseSpinnerBinding
import cz.vvoleman.phr.featureMedicalRecord.ui.model.DiagnoseUiModel

class DiagnoseAdapter(
    private val listener: OnItemClickListener
) : ListAdapter<DiagnoseUiModel, DiagnoseAdapter.DialogOptionsViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DialogOptionsViewHolder {
        val binding = ItemDiagnoseSpinnerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DialogOptionsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DialogOptionsViewHolder, position: Int) {
        val currentItem = getItem(position)

        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    inner class DialogOptionsViewHolder(private val binding: ItemDiagnoseSpinnerBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {

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

        fun bind(item: DiagnoseUiModel) {
            binding.apply {
                radioButton.text = item.name
            }
        }
    }

    interface OnItemClickListener {
        // Returns true for unchecking button
        fun onItemClicked(item: DiagnoseUiModel, position: Int): Boolean
    }

    class DiffCallback : DiffUtil.ItemCallback<DiagnoseUiModel>() {
        override fun areItemsTheSame(oldItem: DiagnoseUiModel, newItem: DiagnoseUiModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: DiagnoseUiModel, newItem: DiagnoseUiModel): Boolean {
            return oldItem.id == newItem.id
        }
    }

    companion object {
        private const val TAG = "DiagnoseAdapter"
    }
}
