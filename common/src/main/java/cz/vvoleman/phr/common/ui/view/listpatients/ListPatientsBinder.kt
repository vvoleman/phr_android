package cz.vvoleman.phr.common.ui.view.listpatients

import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import cz.vvoleman.phr.base.ui.mapper.BaseViewStateBinder
import cz.vvoleman.phr.common.presentation.model.listpatients.ListPatientsViewState
import cz.vvoleman.phr.common.ui.adapter.listpatients.PatientAdapter
import cz.vvoleman.phr.common.ui.mapper.PatientUiModelToPresentationMapper
import cz.vvoleman.phr.common.ui.model.PatientUiModel
import cz.vvoleman.phr.common_datasource.databinding.FragmentListPatientsBinding
import kotlinx.coroutines.CoroutineScope
import java.time.LocalDate

class ListPatientsBinder(
    private val patientUiModelToPresentationMapper: PatientUiModelToPresentationMapper
) : BaseViewStateBinder<ListPatientsViewState, FragmentListPatientsBinding, ListPatientsBinder.Notification>() {

    private lateinit var adapter: PatientAdapter

    override fun init(
        viewBinding: FragmentListPatientsBinding,
        context: Context,
        lifecycleScope: CoroutineScope
    ) {
        super.init(viewBinding, context, lifecycleScope)


        adapter = viewBinding.recyclerViewListPatients.adapter as PatientAdapter
    }

    override fun bind(viewBinding: FragmentListPatientsBinding, viewState: ListPatientsViewState) {
        val list = viewState.patients.map { patientUiModelToPresentationMapper.toUi(it).copy(isSelected = it.id == viewState.selectedPatientId) }

        if (list.isNotEmpty()) {
            adapter.submitList(list)
        }
    }

    sealed class Notification {

    }

}