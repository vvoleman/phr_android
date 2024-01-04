package cz.vvoleman.phr.common.ui.view.problemCategory.list

import cz.vvoleman.phr.base.ui.mapper.BaseViewStateBinder
import cz.vvoleman.phr.common.presentation.model.problemCategory.list.ListProblemCategoryNotification
import cz.vvoleman.phr.common.presentation.model.problemCategory.list.ListProblemCategoryViewState
import cz.vvoleman.phr.common.ui.adapter.problemCategory.ProblemCategoryAdapter
import cz.vvoleman.phr.common.ui.mapper.problemCategory.ProblemCategoryUiModelToPresentationMapper
import cz.vvoleman.phr.common.ui.mapper.problemCategory.ProblemCategoryWithInfoUiModelToPresentationMapper
import cz.vvoleman.phr.common_datasource.databinding.FragmentListProblemCategoryBinding

class ListProblemCategoryBinder(
    private val mapper: ProblemCategoryUiModelToPresentationMapper,
    private val withInfoMapper: ProblemCategoryWithInfoUiModelToPresentationMapper,
) : BaseViewStateBinder<ListProblemCategoryViewState, FragmentListProblemCategoryBinding, ListProblemCategoryNotification>() {

    private var adapter: ProblemCategoryAdapter? = null

    override fun bind(viewBinding: FragmentListProblemCategoryBinding, viewState: ListProblemCategoryViewState) {
        super.bind(viewBinding, viewState)

        val hasProblemCategories = viewState.problemCategories != null

        viewBinding.apply {
            recyclerView.visibility = getVisibility(hasProblemCategories)
            progressBar.visibility = getVisibility(!hasProblemCategories)
        }

        if (hasProblemCategories) {
            adapter?.submitList(
                viewState.problemCategories!!.map {
                    withInfoMapper.toUi(it)
                }
            )
        }
    }

    fun setAdapter(adapter: ProblemCategoryAdapter) {
        this.adapter = adapter
    }

    override fun onDestroy(viewBinding: FragmentListProblemCategoryBinding) {
        super.onDestroy(viewBinding)

        adapter = null
    }

}
