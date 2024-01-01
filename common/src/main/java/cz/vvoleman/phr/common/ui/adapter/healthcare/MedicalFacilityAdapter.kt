package cz.vvoleman.phr.common.ui.adapter.healthcare

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import cz.vvoleman.phr.common.ui.model.healthcare.core.MedicalFacilityUiModel
import cz.vvoleman.phr.common.ui.model.healthcare.core.MedicalFacilityWithAdditionalInfoUiModel
import cz.vvoleman.phr.common_datasource.databinding.ItemItemWithAdditionalInfoBinding

class MedicalFacilityAdapter(
    private val listener: MedicalFacilityAdapterListener
) : ListAdapter<MedicalFacilityWithAdditionalInfoUiModel, MedicalFacilityAdapter.MedicalFacilityViewHolder>(
    DiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicalFacilityViewHolder {
        val binding = ItemItemWithAdditionalInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MedicalFacilityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MedicalFacilityViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class MedicalFacilityViewHolder(private val binding: ItemItemWithAdditionalInfoBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {

        private val _adapter: AdditionalInfoAdapter<MedicalFacilityUiModel>
        init {
            binding.buttonOptions.visibility = View.GONE
            _adapter = AdditionalInfoAdapter(object :
                AdditionalInfoAdapter.AdditionalInfoAdapterListener<MedicalFacilityUiModel> {
                override fun onAdditionalInfoClick(): MedicalFacilityUiModel {
                    return getItemOrNull()!!.medicalFacility
                }
            })
            val flexboxManager = FlexboxLayoutManager(binding.root.context)
            flexboxManager.flexDirection = FlexDirection.ROW
            flexboxManager.alignItems = AlignItems.BASELINE
            flexboxManager.flexWrap = FlexWrap.WRAP
            binding.recyclerViewAdditionalInfo.apply {
                adapter = _adapter
                layoutManager = flexboxManager
//                addItemDecoration(MarginItemDecoration(SizingConstants.MARGIN_SIZE))
                setHasFixedSize(true)
            }
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition

                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)

                    if (item != null) {
                        listener.onItemClick(item.medicalFacility)
                    }
                }
            }
        }

        fun bind(model: MedicalFacilityWithAdditionalInfoUiModel) {
            binding.textViewName.text = model.medicalFacility.fullName
            _adapter.submitList(model.info)
        }

        private fun getItemOrNull(): MedicalFacilityWithAdditionalInfoUiModel? {
            val position = bindingAdapterPosition
            return if (position != RecyclerView.NO_POSITION) {
                getItem(position)
            } else {
                null
            }
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<MedicalFacilityWithAdditionalInfoUiModel>() {
        override fun areItemsTheSame(
            oldItem: MedicalFacilityWithAdditionalInfoUiModel,
            newItem: MedicalFacilityWithAdditionalInfoUiModel
        ): Boolean {
            return oldItem.medicalFacility.id == newItem.medicalFacility.id
        }

        override fun areContentsTheSame(oldItem: MedicalFacilityWithAdditionalInfoUiModel, newItem: MedicalFacilityWithAdditionalInfoUiModel): Boolean {
            return oldItem == newItem
        }
    }

    interface MedicalFacilityAdapterListener {
        fun onItemClick(item: MedicalFacilityUiModel)
    }
}
