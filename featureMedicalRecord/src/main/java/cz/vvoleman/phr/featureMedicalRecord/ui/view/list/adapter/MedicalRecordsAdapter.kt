package cz.vvoleman.phr.featureMedicalRecord.ui.view.list.adapter

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.graphics.ColorUtils
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import cz.vvoleman.phr.common.ui.adapter.grouped.OnAdapterItemListener
import cz.vvoleman.phr.common.utils.getNameOfMonth
import cz.vvoleman.phr.featureMedicalRecord.R
import cz.vvoleman.phr.featureMedicalRecord.databinding.ItemMedicalRecordBinding
import cz.vvoleman.phr.featureMedicalRecord.ui.model.MedicalRecordUiModel

class MedicalRecordsAdapter(
    private val listener: OnAdapterItemListener<MedicalRecordUiModel>
) : ListAdapter<MedicalRecordUiModel, MedicalRecordsAdapter.MedicalRecordViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicalRecordViewHolder {
        val binding =
            ItemMedicalRecordBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MedicalRecordViewHolder(binding, parent.context)
    }

    override fun onBindViewHolder(holder: MedicalRecordViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class MedicalRecordViewHolder(
        private val binding: ItemMedicalRecordBinding,
        private val appContext: Context
    ) :
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
                buttonOptions.setOnClickListener {
                    val position = bindingAdapterPosition

                    if (position != RecyclerView.NO_POSITION) {
                        val item = getItem(position)

                        if (item != null) {
                            listener.onItemOptionsMenuClicked(item, binding.buttonOptions)
                        }
                    }
                }
            }
        }

        fun bind(item: MedicalRecordUiModel) {
            binding.apply {
                textViewDateDay.text = item.visitDate.dayOfMonth.toString()
                textViewDateMonth.text = item.visitDate.getNameOfMonth(true)
                chipMedicalWorker.text = item.medicalWorker
                chipDiagnose.text = item.diagnoseId
                textViewTitle.text = item.problemCategoryName ?: "-"

                if (item.problemCategoryColor != null) {
                    val color = Color.parseColor(item.problemCategoryColor)
                    layoutDate.setBackgroundColor(color)
                    chipMedicalWorker.chipBackgroundColor = ColorStateList.valueOf(color)
                    chipDiagnose.chipBackgroundColor = ColorStateList.valueOf(color)
                }

                val opaqueColor = ColorUtils.setAlphaComponent(
                    chipMedicalWorker.chipBackgroundColor!!.defaultColor,
                    ALPHA
                )
                if (item.medicalWorker == null) {
                    chipMedicalWorker.apply {
                        text = appContext.getString(R.string.medical_record_no_medical_worker)
                        chipBackgroundColor = ColorStateList.valueOf(opaqueColor)
                    }
                }

                if (item.diagnoseId == null) {
                    chipDiagnose.apply {
                        text = appContext.getString(R.string.medical_record_no_diagnose)
                        chipBackgroundColor = ColorStateList.valueOf(opaqueColor)
                        setTextColor(Color.BLACK)
                    }
                }
            }
        }
    }

    companion object {
        private const val ALPHA = 128
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
