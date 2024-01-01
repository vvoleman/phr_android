package cz.vvoleman.phr.common.ui.view.healthcare.list

import com.google.android.material.tabs.TabLayoutMediator
import cz.vvoleman.phr.base.ui.mapper.BaseViewStateBinder
import cz.vvoleman.phr.common.presentation.model.healthcare.list.ListHealthcareNotification
import cz.vvoleman.phr.common.presentation.model.healthcare.list.ListHealthcareViewState
import cz.vvoleman.phr.common.ui.adapter.healthcare.HealthcareFragmentAdapter
import cz.vvoleman.phr.common.ui.mapper.healthcare.MedicalFacilityAdditionalInfoUiModelToPresentationMapper
import cz.vvoleman.phr.common.ui.mapper.healthcare.MedicalWorkerAdditionalInfoUiModelToPresentationMapper
import cz.vvoleman.phr.common_datasource.R
import cz.vvoleman.phr.common_datasource.databinding.FragmentListHealthcareBinding

class ListHealthcareBinder(
    private val workerMapper: MedicalWorkerAdditionalInfoUiModelToPresentationMapper,
    private val facilityMapper: MedicalFacilityAdditionalInfoUiModelToPresentationMapper,
) : BaseViewStateBinder<ListHealthcareViewState, FragmentListHealthcareBinding, ListHealthcareNotification>() {

    private var fragmentAdapter: HealthcareFragmentAdapter? = null

    private var isAdapterSet = false

    override fun bind(viewBinding: FragmentListHealthcareBinding, viewState: ListHealthcareViewState) {
        if (fragmentAdapter != null && !isAdapterSet) {
            bindFragmentAdapter(viewBinding)

        }

        fragmentAdapter?.apply{
            if (viewState.medicalWorkers != null) {
                val workers = workerMapper.toUi(viewState.medicalWorkers)
                setWorkers(workers)
            }

            if (viewState.medicalFacilities != null) {
                val facilities = facilityMapper.toUi(viewState.medicalFacilities)
                setMedicalFacilities(facilities)
            }
        }
    }

    fun setFragmentAdapter(fragmentAdapter: HealthcareFragmentAdapter) {
        isAdapterSet = false
        this.fragmentAdapter = fragmentAdapter
    }

    private fun bindFragmentAdapter(viewBinding: FragmentListHealthcareBinding) {
        viewBinding.viewPager.adapter = fragmentAdapter
        val textIds = listOf(R.string.fragment_worker, R.string.fragment_facility)

        TabLayoutMediator(viewBinding.tabLayout, viewBinding.viewPager) { tab, position ->
            tab.text = fragmentContext.getText(textIds[position])
        }.attach()
        isAdapterSet = true
    }

    override fun onDestroy(viewBinding: FragmentListHealthcareBinding) {
        super.onDestroy(viewBinding)
        fragmentAdapter = null
    }
}
