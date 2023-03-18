package cz.vvoleman.phr.feature_medicalrecord.ui.view.list.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import cz.vvoleman.phr.common.ui.adapter.grouped.OnAdapterItemListener
import cz.vvoleman.phr.feature_medicalrecord.databinding.ItemMedicalRecordBinding
import cz.vvoleman.phr.feature_medicalrecord.ui.model.MedicalRecordUiModel

class MedicalRecordsAdapter (
    private val listener: OnAdapterItemListener<MedicalRecordUiModel>
) : ListAdapter<MedicalRecordUiModel, MedicalRecordsAdapter.MedicalRecordViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicalRecordViewHolder {
        val binding =
            ItemMedicalRecordBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MedicalRecordViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MedicalRecordViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class MedicalRecordViewHolder(private val binding: ItemMedicalRecordBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                root.setOnClickListener {
                    val position = bindingAdapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val item = getItem(position)
                        listener.onItemClicked(item)
                    }
                }
                textViewOptions.setOnClickListener {
                    val position = bindingAdapterPosition

                    if (position != RecyclerView.NO_POSITION) {
                        val item = getItem(position)

                        if (item != null) {
                            listener.onItemOptionsMenuClicked(item, binding.textViewOptions)
                        }
                    };
                }
            }
        }

        fun bind(item: MedicalRecordUiModel) {
            Log.d("MedicalRecordsAdapter", "bind: $item")
            binding.apply {
                textViewDateDay.text = item.createdAt.dayOfMonth.toString()
                textViewDateMonth.text = item.createdAt.month.toString()
                textViewMedicalWorker.text = item.medicalWorker
                textViewProblemCategory.text = item.problemCategoryName
                textViewDiagnose.text = item.diagnoseName

                layoutDate.setBackgroundColor(Color.parseColor(item.problemCategoryColor ?: "#00000000"))
            }
        }

    }

    interface OnItemClickListener {
        fun onItemClicked(item: MedicalRecordUiModel)
        fun onOptionsMenuClick(record: MedicalRecordUiModel, anchorView: View)
    }

    class DiffCallback : DiffUtil.ItemCallback<MedicalRecordUiModel>() {
        override fun areItemsTheSame(
            oldItem: MedicalRecordUiModel,
            newItem: MedicalRecordUiModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: MedicalRecordUiModel,
            newItem: MedicalRecordUiModel
        ): Boolean {
            return oldItem == newItem
        }
    }
}