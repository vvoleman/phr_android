package cz.vvoleman.phr.common.ui.view.healthcare.list

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import cz.vvoleman.phr.base.ui.mapper.ViewStateBinder
import cz.vvoleman.phr.base.ui.view.BaseFragment
import cz.vvoleman.phr.common.presentation.model.healthcare.list.ListHealthcareNotification
import cz.vvoleman.phr.common.presentation.model.healthcare.list.ListHealthcareViewState
import cz.vvoleman.phr.common.presentation.viewmodel.healthcare.ListHealthcareViewModel
import cz.vvoleman.phr.common.ui.adapter.healthcare.HealthcareFragmentAdapter
import cz.vvoleman.phr.common.ui.fragment.healthcare.MedicalFacilityFragment
import cz.vvoleman.phr.common.ui.fragment.healthcare.MedicalWorkerFragment
import cz.vvoleman.phr.common.ui.fragment.healthcare.viewmodel.MedicalFacilityViewModel
import cz.vvoleman.phr.common.ui.fragment.healthcare.viewmodel.MedicalWorkerViewModel
import cz.vvoleman.phr.common.ui.mapper.healthcare.MedicalWorkerUiModelToPresentationMapper
import cz.vvoleman.phr.common.ui.mapper.healthcare.destination.ListHealthcareDestinationUiMapper
import cz.vvoleman.phr.common.ui.model.healthcare.core.MedicalFacilityUiModel
import cz.vvoleman.phr.common.ui.model.healthcare.core.MedicalWorkerUiModel
import cz.vvoleman.phr.common_datasource.R
import cz.vvoleman.phr.common_datasource.databinding.FragmentListHealthcareBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ListHealthcareFragment :
    BaseFragment<ListHealthcareViewState, ListHealthcareNotification, FragmentListHealthcareBinding>(),
    MedicalWorkerFragment.MedicalWorkerFragmentInterface,
    MedicalFacilityFragment.MedicalFacilityFragmentInterface {
    override val viewModel: ListHealthcareViewModel by viewModels()

    private val medicalWorkerViewModel: MedicalWorkerViewModel by viewModels()
    private val medicalFacilityViewModel: MedicalFacilityViewModel by viewModels()

    private lateinit var fragmentAdapter: HealthcareFragmentAdapter

    @Inject
    override lateinit var destinationMapper: ListHealthcareDestinationUiMapper

    @Inject
    lateinit var medicalWorkerMapper: MedicalWorkerUiModelToPresentationMapper

    @Inject
    override lateinit var viewStateBinder: ViewStateBinder<ListHealthcareViewState, FragmentListHealthcareBinding>
    override fun setupBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentListHealthcareBinding {
        return FragmentListHealthcareBinding.inflate(inflater, container, false)
    }

    override fun setupListeners() {
        super.setupListeners()

        medicalWorkerViewModel.setListener(this)
        medicalFacilityViewModel.setListener(this)
        fragmentAdapter = HealthcareFragmentAdapter(medicalWorkerViewModel, medicalFacilityViewModel, this)
        Log.d("ListHealthcareFragment", "setFragmentAdapter")
        (viewStateBinder as ListHealthcareBinder).setFragmentAdapter(fragmentAdapter)

        binding.fabAddWorker.setOnClickListener {
            viewModel.onAddWorker()
        }
    }

    override fun handleNotification(notification: ListHealthcareNotification) {
    }

    override fun onMedicalWorkerClicked(item: MedicalWorkerUiModel) {
        viewModel.onDetailMedicalWorker(medicalWorkerMapper.toPresentation(item))
    }

    override fun onMedicalWorkerDelete(item: MedicalWorkerUiModel) {
        showConfirmDialog(
            title = getString(R.string.dialog_delete_medical_worker_title),
            message = getString(R.string.dialog_delete_medical_worker_message, item.name),
            positiveAction = Pair(getString(R.string.action_delete)) {
                viewModel.onDeleteWorker(medicalWorkerMapper.toPresentation(item))
                showSnackbar(R.string.medical_worker_deleted, Snackbar.LENGTH_SHORT)
            },
            negativeAction = Pair(getString(R.string.action_cancel)) {
                it.dismiss()
            }
        )
    }

    override fun onMedicalWorkerEdit(item: MedicalWorkerUiModel) {
        viewModel.onEditWorker(medicalWorkerMapper.toPresentation(item))
    }

    override fun onMedicalFacilityClick(item: MedicalFacilityUiModel) {
        showSnackbar("Clicked ${item.fullName}", Snackbar.LENGTH_SHORT)
    }
}
