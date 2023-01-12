package cz.vvoleman.phr.ui.medical_records

import android.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import cz.vvoleman.phr.data.medical_records.MedicalRecord
import cz.vvoleman.phr.databinding.ItemMedicalRecordBinding

class MedicalRecordAdapter : ListAdapter<MedicalRecord> {

    inner class ViewHolder(private val binding: ItemMedicalRecordBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {  }
        }

        fun bind(medicalRecord: MedicalRecord) {
            binding.apply {  }
        }

    }

}