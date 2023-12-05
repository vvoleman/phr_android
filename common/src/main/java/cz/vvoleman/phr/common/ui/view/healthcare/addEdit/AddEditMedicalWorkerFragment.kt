package cz.vvoleman.phr.common.ui.view.healthcare.addEdit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.paging.map
import androidx.recyclerview.widget.LinearLayoutManager
import cz.vvoleman.phr.base.ui.mapper.ViewStateBinder
import cz.vvoleman.phr.base.ui.view.BaseFragment
import cz.vvoleman.phr.common.presentation.model.healthcare.addEdit.AddEditMedicalWorkerNotification
import cz.vvoleman.phr.common.presentation.model.healthcare.addEdit.AddEditMedicalWorkerViewState
import cz.vvoleman.phr.common.presentation.viewmodel.healthcare.AddEditMedicalWorkerViewModel
import cz.vvoleman.phr.common.ui.adapter.MarginItemDecoration
import cz.vvoleman.phr.common.ui.mapper.healthcare.MedicalFacilityUiModelToPresentationMapper
import cz.vvoleman.phr.common.ui.mapper.healthcare.destination.AddEditMedicalWorkerDestinationUiMapper
import cz.vvoleman.phr.common.ui.model.healthcare.addEdit.AddEditMedicalServiceItemUiModel
import cz.vvoleman.phr.common.ui.model.healthcare.core.MedicalFacilityUiModel
import cz.vvoleman.phr.common.utils.SizingConstants.MARGIN_SIZE
import cz.vvoleman.phr.common_datasource.databinding.FragmentAddEditMedicalWorkerBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AddEditMedicalWorkerFragment :
    BaseFragment<AddEditMedicalWorkerViewState, AddEditMedicalWorkerNotification, FragmentAddEditMedicalWorkerBinding>(),
    FacilityAdapter.FacilityAdapterListener {

    override val viewModel: AddEditMedicalWorkerViewModel by viewModels()
    private lateinit var facilityAdapter: FacilityAdapter

    @Inject
    override lateinit var destinationMapper: AddEditMedicalWorkerDestinationUiMapper

    @Inject
    override lateinit var viewStateBinder:
        ViewStateBinder<AddEditMedicalWorkerViewState, FragmentAddEditMedicalWorkerBinding>

    @Inject
    lateinit var facilityMapper: MedicalFacilityUiModelToPresentationMapper

    override fun setupBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentAddEditMedicalWorkerBinding {
        return FragmentAddEditMedicalWorkerBinding.inflate(inflater, container, false)
    }

    override fun setupListeners() {
        super.setupListeners()

        facilityAdapter = FacilityAdapter(this)
        (viewStateBinder as AddEditMedicalWorkerBinder).setFacilityAdapter(facilityAdapter)
        binding.recyclerViewFacilities.apply {
            adapter = facilityAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(MarginItemDecoration(MARGIN_SIZE))
            setHasFixedSize(false)
        }

        binding.buttonAddFacility.setOnClickListener {
            viewModel.onAddFacility()
        }
    }

    override fun handleNotification(notification: AddEditMedicalWorkerNotification) {
        when (notification) {
            is AddEditMedicalWorkerNotification.FacilityStreamChanged -> {
            }
        }
    }

    override fun onItemUpdate(item: AddEditMedicalServiceItemUiModel, position: Int) {
        // Update adapters list
        val updatedList = facilityAdapter.currentList.toMutableList()
        updatedList[position] = item

        facilityAdapter.submitList(updatedList)
    }

    override fun onItemDelete(item: AddEditMedicalServiceItemUiModel, position: Int) {
        showSnackbar("onItemDelete ${item.facility?.fullName}")
    }

    override fun onFacilitySearch(query: String, callback: suspend (PagingData<MedicalFacilityUiModel>) -> Unit) {
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
