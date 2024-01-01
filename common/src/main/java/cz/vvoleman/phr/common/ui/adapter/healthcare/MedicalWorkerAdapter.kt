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
import cz.vvoleman.phr.common.ui.model.healthcare.core.MedicalWorkerUiModel
import cz.vvoleman.phr.common.ui.model.healthcare.core.MedicalWorkerWithAdditionalInfoUiModel
import cz.vvoleman.phr.common_datasource.databinding.ItemItemWithAdditionalInfoBinding

class MedicalWorkerAdapter(
    private val listener: MedicalWorkerAdapterListener
) : ListAdapter<MedicalWorkerWithAdditionalInfoUiModel, MedicalWorkerAdapter.MedicalWorkerViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicalWorkerViewHolder {
        val binding = ItemItemWithAdditionalInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MedicalWorkerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MedicalWorkerViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class MedicalWorkerViewHolder(private val binding: ItemItemWithAdditionalInfoBinding) : RecyclerView.ViewHolder(binding.root) {

        private val _adapter: AdditionalInfoAdapter<MedicalWorkerUiModel>
        init {
            _adapter = AdditionalInfoAdapter(object :
                AdditionalInfoAdapter.AdditionalInfoAdapterListener<MedicalWorkerUiModel> {
                override fun onAdditionalInfoClick(): MedicalWorkerUiModel {

                    return getItemOrNull()!!.medicalWorker
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
            binding.buttonOptions.setOnClickListener {
                val position = bindingAdapterPosition

                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)

                    if (item != null) {
                        listener.onItemOptionsMenuClicked(item.medicalWorker, binding.buttonOptions)
                    }
                }
            }
        }

        fun bind(model: MedicalWorkerWithAdditionalInfoUiModel) {
            binding.textViewName.text = model.medicalWorker.name
            _adapter.submitList(model.info)
        }

        private fun getItemOrNull(): MedicalWorkerWithAdditionalInfoUiModel? {
            val position = bindingAdapterPosition
            return if (position != RecyclerView.NO_POSITION) {
                getItem(position)
            } else {
                null
            }
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<MedicalWorkerWithAdditionalInfoUiModel>() {
        override fun areItemsTheSame(oldItem: MedicalWorkerWithAdditionalInfoUiModel, newItem: MedicalWorkerWithAdditionalInfoUiModel): Boolean {
            return oldItem.medicalWorker.id == newItem.medicalWorker.id
        }

        override fun areContentsTheSame(oldItem: MedicalWorkerWithAdditionalInfoUiModel, newItem: MedicalWorkerWithAdditionalInfoUiModel): Boolean {
            return oldItem == newItem
        }
    }

    interface MedicalWorkerAdapterListener {
        fun onItemOptionsMenuClicked(item: MedicalWorkerUiModel, anchorView: View)
    }

}
