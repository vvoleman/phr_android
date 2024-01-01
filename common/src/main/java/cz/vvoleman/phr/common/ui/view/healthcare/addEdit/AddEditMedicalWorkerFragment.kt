package cz.vvoleman.phr.common.ui.view.healthcare.addEdit

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.paging.map
import com.google.android.material.snackbar.Snackbar
import cz.vvoleman.phr.base.ui.mapper.ViewStateBinder
import cz.vvoleman.phr.base.ui.view.BaseFragment
import cz.vvoleman.phr.common.presentation.model.healthcare.addEdit.AddEditMedicalWorkerNotification
import cz.vvoleman.phr.common.presentation.model.healthcare.addEdit.AddEditMedicalWorkerViewState
import cz.vvoleman.phr.common.presentation.model.healthcare.addEdit.AddEditMedicalWorkerViewState.RequiredField
import cz.vvoleman.phr.common.presentation.viewmodel.healthcare.AddEditMedicalWorkerViewModel
import cz.vvoleman.phr.common.ui.component.serviceDetail.ServiceDetail
import cz.vvoleman.phr.common.ui.mapper.healthcare.AddEditMedicalServiceItemUiModelToPresentationMapper
import cz.vvoleman.phr.common.ui.mapper.healthcare.MedicalFacilityUiModelToPresentationMapper
import cz.vvoleman.phr.common.ui.mapper.healthcare.destination.AddEditMedicalWorkerDestinationUiMapper
import cz.vvoleman.phr.common.ui.model.healthcare.addEdit.AddEditMedicalServiceItemUiModel
import cz.vvoleman.phr.common.ui.model.healthcare.core.MedicalFacilityUiModel
import cz.vvoleman.phr.common.utils.textChanges
import cz.vvoleman.phr.common_datasource.R
import cz.vvoleman.phr.common_datasource.databinding.FragmentAddEditMedicalWorkerBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AddEditMedicalWorkerFragment :
    BaseFragment<AddEditMedicalWorkerViewState, AddEditMedicalWorkerNotification, FragmentAddEditMedicalWorkerBinding>(),
    ServiceDetail.ServiceDetailListener {

    override val viewModel: AddEditMedicalWorkerViewModel by viewModels()

    @Inject
    override lateinit var destinationMapper: AddEditMedicalWorkerDestinationUiMapper

    @Inject
    override lateinit var viewStateBinder:
        ViewStateBinder<AddEditMedicalWorkerViewState, FragmentAddEditMedicalWorkerBinding>

    @Inject
    lateinit var facilityMapper: MedicalFacilityUiModelToPresentationMapper

    @Inject
    lateinit var addEditMapper: AddEditMedicalServiceItemUiModelToPresentationMapper

    override fun setupBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentAddEditMedicalWorkerBinding {
        return FragmentAddEditMedicalWorkerBinding.inflate(inflater, container, false)
    }

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    override fun setupListeners() {
        super.setupListeners()

        binding.buttonSave.setOnClickListener {
            viewModel.onSave()
        }

        binding.serviceDetail.setListener(this)

        binding.editTextMedicalWorkerName.textChanges()
            .debounce(500)
            .map { it.toString() }
            .onEach { viewModel.onNameChanged(it) }
            .launchIn(lifecycleScope)
    }

    override fun handleNotification(notification: AddEditMedicalWorkerNotification) {
        when (notification) {
            is AddEditMedicalWorkerNotification.FacilityStreamChanged -> {
            }
            is AddEditMedicalWorkerNotification.MissingFields -> {
                showFieldErrors(notification.fields)
            }
            AddEditMedicalWorkerNotification.CannotSave -> {
                showSnackbar(R.string.error_cannot_save, Snackbar.LENGTH_SHORT)
            }
        }
    }

//    override fun onItemDelete(item: AddEditMedicalServiceItemUiModel, position: Int) {
//        if (item.facility == null) {
//            viewModel.onItemDelete(position)
//            return
//        }
//
//        showConfirmDialog(
//            getString(R.string.dialog_delete_facility_title),
//            getString(R.string.dialog_delete_facility_message, item.facility.fullName),
//            Pair(getString(R.string.action_delete)) { dialog ->
//                showSnackbar(R.string.dialog_delete_facility_done, Snackbar.LENGTH_SHORT, listOf(
//                    Pair(getString(R.string.action_undo)) {
//                        viewModel.onItemUndo(addEditMapper.toPresentation(item), position)
//                    }
//                ))
//                viewModel.onItemDelete(position)
//                dialog.dismiss()
//            },
//            Pair(getString(R.string.action_cancel)) { dialog ->
//                dialog.dismiss()
//            }
//        )
//    }

    private fun showFieldErrors(fields: List<RequiredField>) {
        showSnackbar(R.string.error_missing_fields, Snackbar.LENGTH_SHORT)
        if (fields.contains(RequiredField.NAME)) {
            binding.textInputLayoutMedicalWorkerName.error = getString(R.string.error_required)
        }
        binding.serviceDetail.setError(fields)
    }

    override fun onDataChanged(item: AddEditMedicalServiceItemUiModel) {
        viewModel.onItemUpdate(addEditMapper.toPresentation(item), 0)
    }

    override fun onFacilitySelectorSearch(
        query: String,
        callback: suspend (PagingData<MedicalFacilityUiModel>) -> Unit
    ) {
        Log.d("AddEditMedicalWorker", "onFacilitySearch: $query")
        val stream = viewModel.onFacilitySearch(query)

        lifecycleScope.launch {
            stream.map { pagingData ->
                pagingData.map { facilityMapper.toUi(it) }
            }.collectLatest { pagingData ->
                callback(pagingData)
            }
        }
    }
}
