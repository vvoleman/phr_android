package cz.vvoleman.phr.common.ui.view.patient.listpatients

import android.content.Context
import androidx.lifecycle.LifecycleCoroutineScope
import cz.vvoleman.phr.base.ui.mapper.BaseViewStateBinder
import cz.vvoleman.phr.common.presentation.model.patient.listpatients.ListPatientsViewState
import cz.vvoleman.phr.common.ui.adapter.listpatients.PatientAdapter
import cz.vvoleman.phr.common.ui.mapper.patient.PatientUiModelToPresentationMapper
import cz.vvoleman.phr.common_datasource.databinding.FragmentListPatientsBinding

class ListPatientsBinder(
    private val patientUiModelToPresentationMapper: PatientUiModelToPresentationMapper
) : BaseViewStateBinder<ListPatientsViewState, FragmentListPatientsBinding, ListPatientsBinder.Notification>() {

    private lateinit var adapter: PatientAdapter

    override fun init(
        viewBinding: FragmentListPatientsBinding,
        context: Context,
        lifecycleScope: LifecycleCoroutineScope
    ) {
        super.init(viewBinding, context, lifecycleScope)

        adapter = viewBinding.recyclerViewListPatients.adapter as PatientAdapter
    }

    override fun bind(viewBinding: FragmentListPatientsBinding, viewState: ListPatientsViewState) {
        val list = viewState.patients.map {
            patientUiModelToPresentationMapper.toUi(
                it
            ).copy(isSelected = it.id == viewState.selectedPatientId)
        }

        if (list.isNotEmpty()) {
            adapter.submitList(list)
        }
    }

    sealed class Notification
}
