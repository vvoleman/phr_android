package cz.vvoleman.phr.common.ui.view.healthcare.addEdit

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import cz.vvoleman.phr.common.ui.component.facilitySelector.FacilitySelector
import cz.vvoleman.phr.common.ui.model.healthcare.addEdit.AddEditMedicalServiceItemUiModel
import cz.vvoleman.phr.common.ui.model.healthcare.addEdit.AddEditMedicalServiceItemUiModel.ItemState
import cz.vvoleman.phr.common.ui.model.healthcare.core.MedicalFacilityUiModel
import cz.vvoleman.phr.common.ui.model.healthcare.core.MedicalServiceWithWorkersUiModel
import cz.vvoleman.phr.common.utils.itemChanges
import cz.vvoleman.phr.common.utils.textChanges
import cz.vvoleman.phr.common_datasource.R
import cz.vvoleman.phr.common_datasource.databinding.ItemAddEditFacilityBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class FacilityAdapter(
    private val listener: FacilityAdapterListener,
    private val lifecycleScope: LifecycleCoroutineScope
) : ListAdapter<AddEditMedicalServiceItemUiModel, FacilityAdapter.FacilityAdapterViewHolder>(DiffCallback()),
    FacilitySelector.FacilitySelectorListener {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FacilityAdapterViewHolder {
        val binding = ItemAddEditFacilityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        Log.d(TAG, "Creating new ViewHolder")
        return FacilityAdapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FacilityAdapterViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    inner class FacilityAdapterViewHolder(private val binding: ItemAddEditFacilityBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val context = binding.root.context
        private var isFirstBind = false

        init {
            Log.d(TAG, "init position: $bindingAdapterPosition")
            binding.buttonDelete.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    listener.onItemDelete(item, position)
                }
            }

            binding.autoCompleteTextViewServices.itemChanges()
                .onEach {
                    Log.d("FacilityAdapter", "itemChanges: $it")
                    val item = getItem(bindingAdapterPosition)
                    val copy = item.copy(
                        serviceId = getServiceId(item, it.toString())
                    )
                    listener.onItemUpdate(copy, bindingAdapterPosition)
                }
                .launchIn(lifecycleScope)

            try {
                val combinedFlow = combine(
                    binding.editTextTelephone.textChanges(),
                    binding.editTextEmail.textChanges()
                ) { telephone, email ->
                    Log.d("FacilityAdapter", "combine: $telephone, $email")
                    Pair(telephone.toString(), email.toString())
                }

                combinedFlow
                    .debounce(500)
                    .onEach {
                        Log.d(
                            "FacilityAdapter",
                            "combine onEach: $it. telephone: isFocused: ${binding.editTextTelephone.hasFocus()}"
                        )
                        if (bindingAdapterPosition == RecyclerView.NO_POSITION) {
                            return@onEach
                        }
                        val item = getItem(bindingAdapterPosition)
                        val copy = item.copy(
                            telephone = it.first,
                            email = it.second
                        )
                        listener.onItemUpdate(copy, bindingAdapterPosition)
                    }.launchIn(lifecycleScope)
            } catch (e: Throwable) {
                Log.e("FacilityAdapter", "combine error: ${e.message}")
            }

            binding.facilitySelector.setListener(this@FacilityAdapter)
        }

        fun bind(item: AddEditMedicalServiceItemUiModel) {
            Log.d(TAG, "binding position $bindingAdapterPosition, firstBind: $isFirstBind item: ${item.telephone}")
            if (!isFirstBind) {
                isFirstBind = true
            }
            binding.facilitySelector.setPosition(bindingAdapterPosition)

            val facilityErrorMessage = when (item.facility) {
                null -> context.getString(R.string.error_required)
                else -> null
            }
            binding.facilitySelector.setError(facilityErrorMessage)

            val position = bindingAdapterPosition
            if (position != RecyclerView.NO_POSITION) {
                binding.facilitySelector.setSelected(item.facility)
            }

            val adapterState = item.getState()
            binding.apply {
                textInputLayoutServices.isVisible = adapterState !is ItemState.NoFacility
                textInputLayoutTelephone.isVisible = adapterState is ItemState.Ready
                textInputLayoutEmail.isVisible = adapterState is ItemState.Ready

//                if (!isFirstBind) {
//                    editTextTelephone.setTextIfNotFocused(item.telephone)
//                    editTextEmail.setTextIfNotFocused(item.email)
//                }
            }

            if (adapterState !is ItemState.NoFacility) {
                val serviceErrorMessage = when (adapterState) {
                    is ItemState.Facility -> context.getString(R.string.error_required)
                    else -> null
                }

                val services = getServices(item).mapValues { it.value.medicalService.careField }
                val adapter =
                    ArrayAdapter(binding.root.context, R.layout.item_default, services.values.toList())

                binding.apply {
                    textInputLayoutServices.error = serviceErrorMessage
                    autoCompleteTextViewServices.setAdapter(adapter)
                    autoCompleteTextViewServices.setText(
                        services[item.serviceId] ?: "",
                        false
                    )
                }
            }
        }
    }

    private fun getServices(item: AddEditMedicalServiceItemUiModel): Map<String, MedicalServiceWithWorkersUiModel> {
        // Create map id -> service
        val services = mutableMapOf<String, MedicalServiceWithWorkersUiModel>()
        item.facility?.services?.forEach {
            services[it.medicalService.id] = it
        }
        return services
    }

    private fun getServiceId(item: AddEditMedicalServiceItemUiModel, text: String): String? {
        return item.facility?.services?.find { it.medicalService.careField == text }?.medicalService?.id
    }

    interface FacilityAdapterListener {
        fun onItemUpdateForced(item: AddEditMedicalServiceItemUiModel, position: Int)
        fun onItemUpdate(item: AddEditMedicalServiceItemUiModel, position: Int)
        fun onItemDelete(item: AddEditMedicalServiceItemUiModel, position: Int)

        fun onFacilitySearch(query: String, callback: suspend (PagingData<MedicalFacilityUiModel>) -> Unit)
    }

    private class DiffCallback : DiffUtil.ItemCallback<AddEditMedicalServiceItemUiModel>() {
        override fun areItemsTheSame(
            oldItem: AddEditMedicalServiceItemUiModel,
            newItem: AddEditMedicalServiceItemUiModel
        ): Boolean {
            Log.d(TAG, "areItemsTheSame: ${oldItem.id} == ${newItem.id} -> ${ oldItem.id == newItem.id}}")
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
            val item = getItem(position).copy(
                facility = facility,
                serviceId = null
            )
            listener.onItemUpdateForced(item, position)
        }
    }

    override fun onFacilitySelectorSearch(
        query: String,
        callback: suspend (PagingData<MedicalFacilityUiModel>) -> Unit
    ) {
        listener.onFacilitySearch(query, callback)
    }

    companion object {
        private const val TAG = "FacilityAdapter"
    }
}
