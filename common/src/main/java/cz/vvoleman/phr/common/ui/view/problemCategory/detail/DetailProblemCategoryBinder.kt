package cz.vvoleman.phr.common.ui.view.problemCategory.detail

import androidx.recyclerview.widget.LinearLayoutManager
import com.xwray.groupie.GroupieAdapter
import cz.vvoleman.phr.base.ui.mapper.BaseViewStateBinder
import cz.vvoleman.phr.common.presentation.model.problemCategory.detail.DetailProblemCategoryViewState
import cz.vvoleman.phr.common.ui.adapter.MarginItemDecoration
import cz.vvoleman.phr.common.utils.SizingConstants
import cz.vvoleman.phr.common.utils.toLocalString
import cz.vvoleman.phr.common_datasource.R
import cz.vvoleman.phr.common_datasource.databinding.FragmentDetailProblemCategoryBinding

class DetailProblemCategoryBinder :
    BaseViewStateBinder<DetailProblemCategoryViewState, FragmentDetailProblemCategoryBinding, DetailProblemCategoryBinder.Notification>() {

    override fun firstBind(
        viewBinding: FragmentDetailProblemCategoryBinding,
        viewState: DetailProblemCategoryViewState
    ) {
        super.firstBind(viewBinding, viewState)

        val groupieAdapter = GroupieAdapter().apply {
            addAll(viewState.sections)
        }

        viewBinding.chipCreatedAt.text = viewBinding.root.context.getString(
            R.string.problem_category_detail_created_at,
            viewState.createdAt.toLocalString()
        )

        viewBinding.chipUpdatedAt.text = if (viewState.updatedAt != null) {
            viewBinding.root.context.getString(
                R.string.problem_category_detail_updated_at,
                viewState.updatedAt.toLocalString()
            )
        } else {
            viewBinding.root.context.getString(R.string.problem_category_detail_updated_at_empty)
        }

        viewBinding.recyclerView.apply {
            adapter = groupieAdapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(MarginItemDecoration(SizingConstants.MARGIN_SIZE))
            setHasFixedSize(true)
        }
    }

    override fun onDestroy(viewBinding: FragmentDetailProblemCategoryBinding) {
        viewBinding.recyclerView.adapter = null
        super.onDestroy(viewBinding)
    }

    sealed class Notification {
    }

}
