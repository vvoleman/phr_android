package cz.vvoleman.phr.common.ui.view.listpatients

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import cz.vvoleman.phr.base.ui.mapper.ViewStateBinder
import cz.vvoleman.phr.base.ui.view.BaseFragment
import cz.vvoleman.phr.common.presentation.model.listpatients.ListPatientsNotification
import cz.vvoleman.phr.common.presentation.model.listpatients.ListPatientsViewState
import cz.vvoleman.phr.common.presentation.viewmodel.ListPatientsViewModel
import cz.vvoleman.phr.common.ui.adapter.listpatients.PatientAdapter
import cz.vvoleman.phr.common.ui.mapper.PatientUiModelToPresentationMapper
import cz.vvoleman.phr.common.ui.mapper.destination.ListPatientsDestinationUiMapper
import cz.vvoleman.phr.common.ui.model.PatientUiModel
import cz.vvoleman.phr.common_datasource.R
import cz.vvoleman.phr.common_datasource.databinding.FragmentListPatientsBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ListPatientsFragment :
    BaseFragment<ListPatientsViewState, ListPatientsNotification, FragmentListPatientsBinding>(), PatientAdapter.OnPatientListener {


    override val viewModel: ListPatientsViewModel by viewModels()

    @Inject
    override lateinit var destinationMapper: ListPatientsDestinationUiMapper

    @Inject
    override lateinit var viewStateBinder: ViewStateBinder<ListPatientsViewState, FragmentListPatientsBinding>

    @Inject
    lateinit var patientUiModelToPresentationModelMapper: PatientUiModelToPresentationMapper

    override fun setupBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentListPatientsBinding {
        val binding = FragmentListPatientsBinding.inflate(inflater, container, false)

        val patientAdapter = PatientAdapter(this)
        binding.recyclerViewListPatients.apply {
            adapter = patientAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(false)
        }

        return binding
    }

    override fun setupListeners() {
        super.setupListeners()


    }

    override fun handleNotification(notification: ListPatientsNotification) {
        when (notification) {
            is ListPatientsNotification.PatientAdded -> TODO()
            is ListPatientsNotification.PatientDeleted -> {
                Snackbar.make(
                    binding.root,
                    String.format(getText(R.string.patient_deleted).toString(), notification.patient.name),
                    Snackbar.LENGTH_LONG
                ).show()
            }
            is ListPatientsNotification.PatientEdited -> TODO()
            is ListPatientsNotification.PatientSelected -> TODO()
            is ListPatientsNotification.PatientDeleteFailed -> TODO()
        }
    }

    override fun onPatientClick(patient: PatientUiModel) {
        Log.d(TAG, "onPatientClick: $patient")
    }

    override fun onPatientSwitch(patient: PatientUiModel) {
        viewModel.onPatientSwitch(patient.id)
    }

    override fun onItemOptionsMenuClicked(item: PatientUiModel, anchorView: View) {
        val popup = PopupMenu(requireContext(), anchorView)
        popup.inflate(R.menu.options_patient)

        val presentationModel = patientUiModelToPresentationModelMapper.toPresentation(item)
        popup.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_edit -> {
                    viewModel.onPatientEdit(item.id)
                    true
                }
                R.id.action_delete -> {
                    Log.d(TAG, "onItemOptionsMenuClicked: $item")
                    viewModel.onPatientDelete(presentationModel)
                    true
                }
                else -> false
            }
        }

        popup.show()
    }
}