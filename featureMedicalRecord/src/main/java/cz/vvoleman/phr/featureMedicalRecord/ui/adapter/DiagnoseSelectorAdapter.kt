package cz.vvoleman.phr.featureMedicalRecord.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import cz.vvoleman.phr.featureMedicalRecord.databinding.ItemDiagnoseBinding
import cz.vvoleman.phr.featureMedicalRecord.ui.model.DiagnoseUiModel

class DiagnoseSelectorAdapter(
    private val listener: DiagnoseSelectorAdapterListener
) : PagingDataAdapter<DiagnoseUiModel, DiagnoseSelectorAdapter.DiagnoseSelectorViewHolder>(DiffCallback()) {

    private var selectedItemPosition: Int = RecyclerView.NO_POSITION

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiagnoseSelectorViewHolder {
        val binding = ItemDiagnoseBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return DiagnoseSelectorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DiagnoseSelectorViewHolder, position: Int) {
        val item = getItem(position)

        if (item != null) {
            holder.bind(item, position == selectedItemPosition)
        }
    }

    fun resetSelectedPosition() {
        selectedItemPosition = RecyclerView.NO_POSITION
    }

    inner class DiagnoseSelectorViewHolder(val binding: ItemDiagnoseBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                if (selectedItemPosition == bindingAdapterPosition) {
                    selectedItemPosition = RecyclerView.NO_POSITION
                    listener.onDiagnoseSelected(null)
                    notifyItemChanged(bindingAdapterPosition)
                    return@setOnClickListener
                }

                val previousSelectedItemPosition = selectedItemPosition
                selectedItemPosition = bindingAdapterPosition
                notifyItemChanged(selectedItemPosition)
                if (previousSelectedItemPosition != RecyclerView.NO_POSITION) {
                    notifyItemChanged(previousSelectedItemPosition)
                }
                listener.onDiagnoseSelected(getItem(selectedItemPosition))
            }
        }

        fun bind(item: DiagnoseUiModel, isSelected: Boolean) {
            binding.textViewId.text = item.id
            binding.textViewName.text = item.name

            if (isSelected) {
                binding.root.setBackgroundResource(cz.vvoleman.phr.base.R.color.beige_200)
            } else {
                binding.root.setBackgroundResource(cz.vvoleman.phr.base.R.color.white)
            }
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<DiagnoseUiModel>() {
        override fun areItemsTheSame(oldItem: DiagnoseUiModel, newItem: DiagnoseUiModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DiagnoseUiModel, newItem: DiagnoseUiModel): Boolean {
            return oldItem == newItem
        }
    }

    interface DiagnoseSelectorAdapterListener {
        fun onDiagnoseSelected(diagnose: DiagnoseUiModel?)
    }
}
