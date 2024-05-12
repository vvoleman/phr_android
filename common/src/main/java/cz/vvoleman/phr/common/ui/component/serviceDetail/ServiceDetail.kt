package cz.vvoleman.phr.common.ui.component.serviceDetail

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.paging.PagingData
import cz.vvoleman.phr.common.presentation.model.healthcare.addEdit.AddEditMedicalWorkerViewState.RequiredField
import cz.vvoleman.phr.common.ui.component.facilitySelector.FacilitySelector
import cz.vvoleman.phr.common.ui.model.healthcare.addEdit.AddEditMedicalServiceItemUiModel
import cz.vvoleman.phr.common.ui.model.healthcare.addEdit.AddEditMedicalServiceItemUiModel.ItemState
import cz.vvoleman.phr.common.ui.model.healthcare.core.MedicalFacilityUiModel
import cz.vvoleman.phr.common.ui.model.healthcare.core.MedicalServiceWithWorkersUiModel
import cz.vvoleman.phr.common.utils.TimeConstants
import cz.vvoleman.phr.common.utils.itemChanges
import cz.vvoleman.phr.common.utils.textChanges
import cz.vvoleman.phr.common_datasource.R
import cz.vvoleman.phr.common_datasource.databinding.ViewServiceDetailBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Suppress("TooManyFunctions")
class ServiceDetail @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    val binding: ViewServiceDetailBinding
    private var item: AddEditMedicalServiceItemUiModel? = null
    private var _listener: ServiceDetailListener? = null
    private var _lifecycleScope: LifecycleCoroutineScope? = null

    init {
        binding = ViewServiceDetailBinding.inflate(LayoutInflater.from(context), this, true)
    }

    fun setListener(listener: ServiceDetailListener) {
        _listener = listener
    }

    fun setLifecycleScope(lifecycleScope: LifecycleCoroutineScope) {
        _lifecycleScope = lifecycleScope
    }

    private fun itemChanged(
        facility: MedicalFacilityUiModel? = null,
        serviceId: String? = null,
        telephone: String? = null,
        email: String? = null
    ) {
        val newItem = item!!.copy(
            facility = facility ?: item!!.facility,
            serviceId = serviceId ?: item!!.serviceId,
            telephone = telephone ?: item!!.telephone,
            email = email ?: item!!.email
        )

        itemChanged(newItem)
    }

    private fun itemChanged(item: AddEditMedicalServiceItemUiModel) {
        _listener?.onDataChanged(item)
    }

    @Suppress("UnusedParameter")
    private fun redrawByState(oldState: ItemState, newState: ItemState) {
        binding.apply {
            textInputLayoutServices.isVisible = newState !is ItemState.NoFacility
            textInputLayoutTelephone.isVisible = newState is ItemState.Ready
            textInputLayoutEmail.isVisible = newState is ItemState.Ready
        }
    }

    fun setItem(item: AddEditMedicalServiceItemUiModel) {
        Log.d("ServiceDetail", "setItem: $item")
        if (this.item == null) {
            init(item)
            return
        }

        val oldState = this.item!!.getState()
        val newState = item.getState()
        Log.d("ServiceDetail", "setItem: $oldState \n -> $newState")
        this.item = item
        redrawServices(oldState, newState)
        redrawByState(oldState, newState)
    }

    fun setError(fieldErrors: List<RequiredField>) {
        binding.apply {
            textInputLayoutServices.error = getErrorOrNull(fieldErrors.contains(RequiredField.SERVICE))
            facilitySelector.setError(getErrorOrNull(fieldErrors.contains(RequiredField.FACILITY)))
        }
        Log.d("ServiceDetail", "setError: $fieldErrors, ${getErrorOrNull(fieldErrors.contains(RequiredField.SERVICE))}")
    }

    private fun getErrorOrNull(condition: Boolean, message: Int = R.string.error_required): String? =
        if (condition) context.getString(message) else null

    private fun init(item: AddEditMedicalServiceItemUiModel) {
        this.item = item

        val state = item.getState()
        binding.facilitySelector.setSelected(item.facility)
        binding.editTextEmail.setText(item.email)
        binding.editTextTelephone.setText(item.telephone)

        redrawByState(state, state)
        redrawServices(state, state)

        initListeners(item)
    }

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    private fun initListeners(item: AddEditMedicalServiceItemUiModel) {
        val lifecycleScope = _lifecycleScope!!

        binding.facilitySelector.setListener(object : FacilitySelector.FacilitySelectorListener {
            override fun onFacilitySelected(facility: MedicalFacilityUiModel?, position: Int?) {
                if (facility != null) {
                    binding.facilitySelector.setError(null)
                }

                val copy = item.copy(facility = facility, serviceId = null)
                itemChanged(copy)
            }

            override fun onFacilitySelectorSearch(
                query: String,
                callback: suspend (PagingData<MedicalFacilityUiModel>) -> Unit
            ) {
                _listener?.onFacilitySelectorSearch(query, callback)
            }
        })

        binding.autoCompleteTextViewServices.itemChanges()
            .onEach {
                val detail = this.item
                if (detail == null) {
                    Log.d("FacilityAdapter", "autoCompleteTextViewServices: detail is null")
                    return@onEach
                }
                binding.textInputLayoutServices.error = null
                val serviceId = getServiceId(detail, it as String)
                Log.d("FacilityAdapter", "autoCompleteTextViewServices: $it")
                itemChanged(serviceId = serviceId)
            }
            .launchIn(lifecycleScope)

        binding.editTextEmail.textChanges()
            .debounce(TimeConstants.DEBOUNCE_TIME)
            .onEach { itemChanged(email = it.toString()) }
            .launchIn(lifecycleScope)

        binding.editTextTelephone.textChanges()
            .debounce(TimeConstants.DEBOUNCE_TIME)
            .onEach { itemChanged(telephone = it.toString()) }
            .launchIn(lifecycleScope)
    }

    @Suppress("UnusedParameter")
    private fun redrawServices(oldState: ItemState, newState: ItemState) {
        if (newState !is ItemState.NoFacility) {
            val services = getServices(item!!).mapValues { it.value.medicalService.careField }
            val adapter =
                ArrayAdapter(binding.root.context, R.layout.item_default, services.values.toList())

            binding.autoCompleteTextViewServices.setAdapter(adapter)
            binding.autoCompleteTextViewServices.setText(
                services[item!!.serviceId],
                false
            )
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
        return item.facility
            ?.services
            ?.find {
                it.medicalService.careField == text
            }
            ?.medicalService?.id
    }

    interface ServiceDetailListener {
        fun onDataChanged(item: AddEditMedicalServiceItemUiModel)
        fun onFacilitySelectorSearch(query: String, callback: suspend (PagingData<MedicalFacilityUiModel>) -> Unit)
    }
}
