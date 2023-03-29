package cz.vvoleman.phr.common.ui.adapter.listpatients

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import cz.vvoleman.phr.common.ui.model.PatientUiModel
import cz.vvoleman.phr.common_datasource.databinding.ItemPatientBinding

class PatientAdapter :
    ListAdapter<PatientUiModel, PatientAdapter.PatientViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientViewHolder {
        Log.d("PatientAdapter", "onCreateViewHolder")
        val binding = ItemPatientBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PatientViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PatientViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class PatientViewHolder(private val binding: ItemPatientBinding) :
        RecyclerView.ViewHolder(binding.root) {

            init {
                binding.apply {
                    root.setOnClickListener {
                        val position = adapterPosition
                        if (position != RecyclerView.NO_POSITION) {
                            val item = getItem(position)
                            Log.d("PatientAdapter", "Clicked on ${item.name}")
                        }
                    }
                }
            }

            fun bind(item: PatientUiModel) {
                binding.apply {
                    textViewName.text = item.name
                }
            }

    }

    class DiffCallback : DiffUtil.ItemCallback<PatientUiModel>() {

        override fun areItemsTheSame(oldItem: PatientUiModel, newItem: PatientUiModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PatientUiModel, newItem: PatientUiModel): Boolean {
            return oldItem == newItem
        }

    }

}