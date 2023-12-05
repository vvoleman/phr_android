package cz.vvoleman.phr.common.ui.component.facilitySelector

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import cz.vvoleman.phr.common.domain.facade.AddressFacade
import cz.vvoleman.phr.common.ui.model.healthcare.core.MedicalFacilityUiModel
import cz.vvoleman.phr.common_datasource.databinding.ItemFacilityBinding

class FacilitySelectorAdapter(
    private val listener: FacilitySelectorAdapterListener
) : PagingDataAdapter<MedicalFacilityUiModel, FacilitySelectorAdapter.FacilitySelectorViewHolder>(DiffCallback()) {

    private var selectedItemPosition: Int = RecyclerView.NO_POSITION

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FacilitySelectorViewHolder {
        val binding = ItemFacilityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FacilitySelectorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FacilitySelectorViewHolder, position: Int) {
        val item = getItem(position)

        if (item != null) {
            holder.bind(item, position == selectedItemPosition)
        }
    }

    fun resetSelectedPosition() {
        selectedItemPosition = RecyclerView.NO_POSITION
    }

    inner class FacilitySelectorViewHolder(private val binding: ItemFacilityBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {

        init {
            binding.root.setOnClickListener {
                if (selectedItemPosition == bindingAdapterPosition) {
                    selectedItemPosition = RecyclerView.NO_POSITION
                    listener.onFacilitySelected(null)
                    notifyItemChanged(bindingAdapterPosition)
                    return@setOnClickListener
                }

                val previousSelectedItemPosition = selectedItemPosition
                selectedItemPosition = bindingAdapterPosition
                notifyItemChanged(selectedItemPosition)
                if (previousSelectedItemPosition != RecyclerView.NO_POSITION) {
                    notifyItemChanged(previousSelectedItemPosition)
                }
                listener.onFacilitySelected(getItem(selectedItemPosition))
            }
        }

        fun bind(item: MedicalFacilityUiModel, isSelected: Boolean) {
            binding.apply {
                textViewName.text = item.fullName
                textViewAddress.text = AddressFacade.formatAddress(
                    item.street,
                    item.houseNumber,
                    item.zipCode,
                    item.city
                )
                textViewFacilityType.text = item.facilityType
                textViewTelephone.text = item.telephone

                if (isSelected) {
                    root.setBackgroundResource(cz.vvoleman.phr.base.R.color.beige_200)
                } else {
                    root.setBackgroundResource(cz.vvoleman.phr.base.R.color.white)
                }
            }
        }
    }

    interface FacilitySelectorAdapterListener {
        fun onFacilitySelected(item: MedicalFacilityUiModel?)
    }

    private class DiffCallback : DiffUtil.ItemCallback<MedicalFacilityUiModel>() {
        override fun areItemsTheSame(oldItem: MedicalFacilityUiModel, newItem: MedicalFacilityUiModel): Boolean {
            Log.d("FacilitySelectorAdapter", "areItemsTheSame: ${oldItem.id} == ${newItem.id}")
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MedicalFacilityUiModel, newItem: MedicalFacilityUiModel): Boolean {
            return oldItem == newItem
        }
    }
}
