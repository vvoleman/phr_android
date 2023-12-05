package cz.vvoleman.phr.common.ui.view.healthcare.addEdit

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import cz.vvoleman.phr.common.ui.component.facilitySelector.FacilitySelector
import cz.vvoleman.phr.common.ui.model.healthcare.addEdit.AddEditMedicalServiceItemUiModel
import cz.vvoleman.phr.common.ui.model.healthcare.core.MedicalFacilityUiModel
import cz.vvoleman.phr.common_datasource.databinding.ItemAddEditFacilityBinding

class FacilityAdapter(
    private val listener: FacilityAdapterListener
) : ListAdapter<AddEditMedicalServiceItemUiModel, FacilityAdapter.FacilityAdapterViewHolder>(DiffCallback()),
    FacilitySelector.FacilitySelectorListener {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FacilityAdapterViewHolder {
        Log.d("FacilityAdapter", "onCreateViewHolder")
        val binding = ItemAddEditFacilityBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return FacilityAdapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FacilityAdapterViewHolder, position: Int) {
        val item = getItem(holder.absoluteAdapterPosition)
        holder.bind(item)
    }

    inner class FacilityAdapterViewHolder(private val binding: ItemAddEditFacilityBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.buttonDelete.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    listener.onItemDelete(item, position)
                }
            }

            binding.facilitySelector.setListener(this@FacilityAdapter)
        }

        fun bind(item: AddEditMedicalServiceItemUiModel) {
            Log.d("FacilityAdapter", "bind: $item")
            binding.facilitySelector.setPosition(bindingAdapterPosition)
            val position = bindingAdapterPosition
            if (position != RecyclerView.NO_POSITION) {
                binding.facilitySelector.setSelected(item.facility)
            }
            binding.apply {
                editTextTelephone.setText(item.telephone)
                editTextEmail.setText(item.email)
                val services = item.facility?.services?.map { it.medicalService.careType } ?: emptyList()
                (autoCompleteTextViewServices as MaterialAutoCompleteTextView).setSimpleItems(services.toTypedArray())
            }
        }
    }

    interface FacilityAdapterListener {
        fun onItemUpdate(item: AddEditMedicalServiceItemUiModel, position: Int)
        fun onItemDelete(item: AddEditMedicalServiceItemUiModel, position: Int)

        fun onFacilitySearch(query: String, callback: suspend (PagingData<MedicalFacilityUiModel>) -> Unit)
    }

    private class DiffCallback : DiffUtil.ItemCallback<AddEditMedicalServiceItemUiModel>() {
        override fun areItemsTheSame(
            oldItem: AddEditMedicalServiceItemUiModel,
            newItem: AddEditMedicalServiceItemUiModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: AddEditMedicalServiceItemUiModel,
            newItem: AddEditMedicalServiceItemUiModel
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun onFacilitySelected(facility: MedicalFacilityUiModel?, position: Int?) {
        if (position != null) {
            val item = getItem(position).copy(facility = facility)
            listener.onItemUpdate(item, position)
        }
    }

    override fun onFacilitySelectorSearch(query: String, callback: suspend (PagingData<MedicalFacilityUiModel>) -> Unit) {
        Log.d("FacilityAdapter", "onFacilitySelectorSearch: $query")
        listener.onFacilitySearch(query, callback)
    }
}
