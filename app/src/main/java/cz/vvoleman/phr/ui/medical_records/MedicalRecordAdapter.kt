package cz.vvoleman.phr.ui.medical_records

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import cz.vvoleman.phr.data.medical_records.MedicalRecord
import cz.vvoleman.phr.data.medical_records.MedicalRecordWithDetails
import cz.vvoleman.phr.databinding.ItemMedicalRecordBinding
import cz.vvoleman.phr.util.getCurrentDay
import cz.vvoleman.phr.util.getCurrentMonth
import cz.vvoleman.phr.util.getNameOfDay
import cz.vvoleman.phr.util.getNameOfMonth

class MedicalRecordAdapter(
    private val listener: OnItemClickListener
) : ListAdapter<MedicalRecordWithDetails, MedicalRecordAdapter.MedicalRecordViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicalRecordViewHolder {
        val binding = ItemMedicalRecordBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MedicalRecordViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MedicalRecordViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class MedicalRecordViewHolder(private val binding: ItemMedicalRecordBinding) : RecyclerView.ViewHolder(binding.root) {

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
                            listener.onOptionsMenuClick(item, binding.textViewOptions)
                        }
                    };
                }
            }
        }

        fun bind(item: MedicalRecordWithDetails) {
            binding.apply {
                textViewDateDay.text = item.medicalRecord.date.getCurrentDay().toString()+"."
                textViewDateMonth.text = item.medicalRecord.date.getNameOfMonth(true)
                textViewFacility.text = item.facility.name
                textViewDoctor.text = item.patient.name
                textViewDiagnose.text = item.diagnose.name
            }
        }

    }

    interface OnItemClickListener {
        fun onItemClicked(item: MedicalRecordWithDetails)
        fun onOptionsMenuClick(record: MedicalRecordWithDetails, anchorView: View)
    }

    class DiffCallback : DiffUtil.ItemCallback<MedicalRecordWithDetails>() {
        override fun areItemsTheSame(oldItem: MedicalRecordWithDetails, newItem: MedicalRecordWithDetails): Boolean {
            return oldItem.medicalRecord.recordId == newItem.medicalRecord.recordId
        }

        override fun areContentsTheSame(
            oldItem: MedicalRecordWithDetails,
            newItem: MedicalRecordWithDetails
        ): Boolean {
            return oldItem == newItem
        }
    }

}