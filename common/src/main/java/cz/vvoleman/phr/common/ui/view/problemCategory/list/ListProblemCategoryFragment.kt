package cz.vvoleman.phr.common.ui.view.problemCategory.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import cz.vvoleman.phr.base.presentation.viewmodel.BaseViewModel
import cz.vvoleman.phr.base.ui.mapper.DestinationUiMapper
import cz.vvoleman.phr.base.ui.mapper.ViewStateBinder
import cz.vvoleman.phr.base.ui.view.BaseFragment
import cz.vvoleman.phr.common.presentation.model.problemCategory.list.ListProblemCategoryNotification
import cz.vvoleman.phr.common.presentation.model.problemCategory.list.ListProblemCategoryViewState
import cz.vvoleman.phr.common.presentation.viewmodel.problemCategory.ListProblemCategoryViewModel
import cz.vvoleman.phr.common.ui.adapter.MarginItemDecoration
import cz.vvoleman.phr.common.ui.adapter.problemCategory.ProblemCategoryAdapter
import cz.vvoleman.phr.common.ui.mapper.problemCategory.ProblemCategoryUiModelToPresentationMapper
import cz.vvoleman.phr.common.ui.model.problemCategory.ProblemCategoryWithInfoUiModel
import cz.vvoleman.phr.common.utils.SizingConstants
import cz.vvoleman.phr.common_datasource.R
import cz.vvoleman.phr.common_datasource.databinding.FragmentListProblemCategoryBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ListProblemCategoryFragment: BaseFragment<ListProblemCategoryViewState, ListProblemCategoryNotification, FragmentListProblemCategoryBinding>(),
    ProblemCategoryAdapter.ProblemCategoryListener {

    override val viewModel: ListProblemCategoryViewModel by viewModels()

    @Inject
    override lateinit var destinationMapper: DestinationUiMapper

    @Inject
    override lateinit var viewStateBinder: ViewStateBinder<ListProblemCategoryViewState, FragmentListProblemCategoryBinding>

    @Inject
    lateinit var problemCategoryMapper: ProblemCategoryUiModelToPresentationMapper

    override fun setupBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentListProblemCategoryBinding {
        return FragmentListProblemCategoryBinding.inflate(inflater, container, false)
    }

    override fun setupListeners() {
        super.setupListeners()

        val _adapter = ProblemCategoryAdapter(this)
        val binder = viewStateBinder as ListProblemCategoryBinder
        binder.setAdapter(_adapter)

        binding.recyclerView.apply {
            adapter = _adapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(MarginItemDecoration(SizingConstants.MARGIN_SIZE))
            setHasFixedSize(false)
        }
    }

    override fun handleNotification(notification: ListProblemCategoryNotification) {
        TODO("Not yet implemented")
    }

    override fun onClick(problemCategory: ProblemCategoryWithInfoUiModel) {
        viewModel.onDetail(problemCategoryMapper.toPresentation(problemCategory.problemCategory))
    }

    override fun onOptionsMenuClick(problemCategory: ProblemCategoryWithInfoUiModel, anchorView: View) {
        val popup = PopupMenu(requireContext(), anchorView)
        popup.inflate(R.menu.options_problem_category)

        val presentationModel = problemCategoryMapper.toPresentation(problemCategory.problemCategory)
        popup.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_edit -> {
                    viewModel.onEdit(presentationModel)
                    true
                }
                R.id.action_delete -> {
                    viewModel.onDelete(presentationModel)
                    true
                }
                else -> false
            }
        }

        popup.show()
    }
}
