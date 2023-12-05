package cz.vvoleman.phr.common.ui.adapter.listpatients

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import cz.vvoleman.phr.common.ui.model.patient.PatientUiModel
import cz.vvoleman.phr.common.utils.getNameOfMonth
import cz.vvoleman.phr.common_datasource.databinding.ItemPatientBinding

class PatientAdapter(
    private val listener: OnPatientListener
) :
    ListAdapter<PatientUiModel, PatientAdapter.PatientViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientViewHolder {
        Log.d("PatientAdapter", "onCreateViewHolder")
        val binding = ItemPatientBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PatientViewHolder(binding, parent.context)
    }

    override fun onBindViewHolder(holder: PatientViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class PatientViewHolder(private val binding: ItemPatientBinding, private val appContext: Context) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                root.setOnClickListener {
                    val position = bindingAdapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val item = getItem(position)
                        listener.onPatientClick(item)
                    }
                }
                buttonSwitch.setOnClickListener {
                    val position = bindingAdapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val item = getItem(position)
                        listener.onPatientSwitch(item)
                    }
                }
                buttonOptions.setOnClickListener {
                    val position = bindingAdapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val item = getItem(position)
                        listener.onItemOptionsMenuClicked(item, binding.buttonOptions)
                    }
                }
            }
        }

        fun bind(item: PatientUiModel) {
            binding.apply {
                textViewTitle.text = item.name

                item.birthDate?.let {
                    textViewDateYear.text = it.year.toString()
                    textViewDateDayMonth.text = "${it.dayOfMonth}. ${it.getNameOfMonth()}"
                }

                buttonSwitch.isEnabled = !item.isSelected

                if (item.isSelected) {
                    layoutDate.setBackgroundColor(appContext.getColor(cz.vvoleman.phr.base.R.color.green_700))
                }
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<PatientUiModel>() {

        override fun areItemsTheSame(oldItem: PatientUiModel, newItem: PatientUiModel): Boolean {
            Log.d("PatientAdapter", "areItemsTheSame: ${oldItem.id} == ${newItem.id}")
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PatientUiModel, newItem: PatientUiModel): Boolean {
            Log.d("PatientAdapter", "areContentsTheSame: ${oldItem.name} == ${newItem.name}")
            return oldItem == newItem
        }
    }

    interface OnPatientListener {
        fun onPatientClick(patient: PatientUiModel)
        fun onPatientSwitch(patient: PatientUiModel)
        fun onItemOptionsMenuClicked(item: PatientUiModel, anchorView: View)
    }
}
