package cz.vvoleman.phr.common.ui.view.healthcare.detailMedicalWorker

import com.xwray.groupie.GroupieAdapter
import cz.vvoleman.phr.base.ui.mapper.BaseViewStateBinder
import cz.vvoleman.phr.common.presentation.model.healthcare.detailMedicalWorker.DetailMedicalWorkerViewState
import cz.vvoleman.phr.common.ui.adapter.MarginItemDecoration
import cz.vvoleman.phr.common.ui.factory.DetailMedicalWorkerContainerFactory
import cz.vvoleman.phr.common.ui.mapper.healthcare.MedicalFacilityUiModelToPresentationMapper
import cz.vvoleman.phr.common.ui.mapper.healthcare.SpecificMedicalWorkerUiModelToPresentationMapper
import cz.vvoleman.phr.common.utils.SizingConstants
import cz.vvoleman.phr.common_datasource.databinding.FragmentDetailMedicalWorkerBinding

class DetailMedicalWorkerBinder(
    private val specificWorkerMapper: SpecificMedicalWorkerUiModelToPresentationMapper,
    private val facilityMapper: MedicalFacilityUiModelToPresentationMapper,
    private val factory: DetailMedicalWorkerContainerFactory
) :
    BaseViewStateBinder<DetailMedicalWorkerViewState, FragmentDetailMedicalWorkerBinding, DetailMedicalWorkerBinder.Notification>() {

    override fun firstBind(viewBinding: FragmentDetailMedicalWorkerBinding, viewState: DetailMedicalWorkerViewState) {
        super.firstBind(viewBinding, viewState)

        viewBinding.textViewWorkerName.text = viewState.medicalWorker.name

        val workers = specificWorkerMapper.toUi(viewState.specificWorkers)
        val facilities = facilityMapper.toUi(viewState.facilities)
        val groupieAdapter = GroupieAdapter().apply {
            addAll(factory.create(workers, facilities))
        }

        viewBinding.recyclerView.apply {
            adapter = groupieAdapter
            layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
            addItemDecoration(MarginItemDecoration(SizingConstants.MARGIN_SIZE))
            setHasFixedSize(true)
        }
    }

    sealed class Notification

}
