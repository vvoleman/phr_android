package cz.vvoleman.phr.common.ui.view.healthcare.list

import com.google.android.material.tabs.TabLayoutMediator
import cz.vvoleman.phr.base.ui.mapper.BaseViewStateBinder
import cz.vvoleman.phr.common.presentation.model.healthcare.list.ListHealthcareNotification
import cz.vvoleman.phr.common.presentation.model.healthcare.list.ListHealthcareViewState
import cz.vvoleman.phr.common.ui.adapter.healthcare.HealthcareFragmentAdapter
import cz.vvoleman.phr.common_datasource.R
import cz.vvoleman.phr.common_datasource.databinding.FragmentListHealthcareBinding

class ListHealthcareBinder : BaseViewStateBinder<ListHealthcareViewState, FragmentListHealthcareBinding, ListHealthcareNotification>() {

    private var fragmentAdapter: HealthcareFragmentAdapter? = null

    private var isAdapterSet = false

    override fun bind(viewBinding: FragmentListHealthcareBinding, viewState: ListHealthcareViewState) {
        if (fragmentAdapter != null && !isAdapterSet) {
            bindFragmentAdapter(viewBinding)
        }
    }

    fun setFragmentAdapter(fragmentAdapter: HealthcareFragmentAdapter) {
        this.fragmentAdapter = fragmentAdapter
    }

    private fun bindFragmentAdapter(viewBinding: FragmentListHealthcareBinding) {
        viewBinding.viewPager.adapter = fragmentAdapter
        val textIds = listOf(R.string.fragment_worker, R.string.fragment_facility)

        TabLayoutMediator(viewBinding.tabLayout, viewBinding.viewPager) { tab, position ->
            tab.text = fragmentContext.getText(textIds[position])
        }.attach()
    }
}
