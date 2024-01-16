package cz.vvoleman.phr.featureMedicalRecord.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import cz.vvoleman.phr.featureMedicalRecord.databinding.ItemDiagnoseBinding
import cz.vvoleman.phr.featureMedicalRecord.ui.model.DiagnoseItemUiModel

class DiagnoseSelectorAdapter(
    private val listener: DiagnoseSelectorAdapterListener
) : PagingDataAdapter<DiagnoseItemUiModel, DiagnoseSelectorAdapter.DiagnoseSelectorViewHolder>(DiffCallback()) {

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
        fun bind(item: DiagnoseItemUiModel, isSelected: Boolean) {
            binding.textViewName.text = item.name
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<DiagnoseItemUiModel>() {
        override fun areItemsTheSame(oldItem: DiagnoseItemUiModel, newItem: DiagnoseItemUiModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DiagnoseItemUiModel, newItem: DiagnoseItemUiModel): Boolean {
            return oldItem == newItem
        }
    }

    interface DiagnoseSelectorAdapterListener {
        fun onDiagnoseSelected(diagnose: DiagnoseItemUiModel?)
    }

}
