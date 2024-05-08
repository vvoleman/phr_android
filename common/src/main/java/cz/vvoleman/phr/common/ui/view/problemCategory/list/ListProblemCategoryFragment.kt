package cz.vvoleman.phr.common.ui.view.problemCategory.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import cz.vvoleman.phr.base.ui.mapper.ViewStateBinder
import cz.vvoleman.phr.base.ui.view.BaseFragment
import cz.vvoleman.phr.common.domain.model.problemCategory.request.DataDeleteType
import cz.vvoleman.phr.common.presentation.model.problemCategory.ProblemCategoryPresentationModel
import cz.vvoleman.phr.common.presentation.model.problemCategory.list.ListProblemCategoryNotification
import cz.vvoleman.phr.common.presentation.model.problemCategory.list.ListProblemCategoryViewState
import cz.vvoleman.phr.common.presentation.viewmodel.problemCategory.ListProblemCategoryViewModel
import cz.vvoleman.phr.common.ui.adapter.MarginItemDecoration
import cz.vvoleman.phr.common.ui.adapter.problemCategory.ProblemCategoryAdapter
import cz.vvoleman.phr.common.ui.mapper.problemCategory.ProblemCategoryUiModelToPresentationMapper
import cz.vvoleman.phr.common.ui.mapper.problemCategory.destination.ListProblemCategoryDestinationUiMapper
import cz.vvoleman.phr.common.ui.model.problemCategory.ProblemCategoryWithInfoUiModel
import cz.vvoleman.phr.common.utils.SizingConstants
import cz.vvoleman.phr.common_datasource.R
import cz.vvoleman.phr.common_datasource.databinding.FragmentListProblemCategoryBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ListProblemCategoryFragment :
    BaseFragment<ListProblemCategoryViewState, ListProblemCategoryNotification, FragmentListProblemCategoryBinding>(),
    ProblemCategoryAdapter.ProblemCategoryListener {

    override val viewModel: ListProblemCategoryViewModel by viewModels()

    @Inject
    override lateinit var destinationMapper: ListProblemCategoryDestinationUiMapper

    @Inject
    override lateinit var viewStateBinder: ViewStateBinder<ListProblemCategoryViewState, FragmentListProblemCategoryBinding>

    @Inject
    lateinit var problemCategoryMapper: ProblemCategoryUiModelToPresentationMapper

    override fun setupBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentListProblemCategoryBinding {
        return FragmentListProblemCategoryBinding.inflate(inflater, container, false)
    }

    override fun setupListeners() {
        super.setupListeners()

        val adapter = ProblemCategoryAdapter(this)
        val binder = viewStateBinder as ListProblemCategoryBinder
        binder.setAdapter(adapter)

        binding.recyclerView.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(MarginItemDecoration(SizingConstants.MARGIN_SIZE))
            setHasFixedSize(false)
        }

        binding.fabAddProblemCategory.setOnClickListener {
            viewModel.onCreate()
        }
    }

    override fun handleNotification(notification: ListProblemCategoryNotification) {
        when (notification) {
            is ListProblemCategoryNotification.Deleted -> {
                showSnackbar(R.string.problem_category_deleted)
            }

            is ListProblemCategoryNotification.DeleteFailed -> {
                showSnackbar(R.string.problem_category_delete_failed)
            }
        }
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
                R.id.action_detail -> {
                    viewModel.onDetail(presentationModel)
                    true
                }
                R.id.action_edit -> {
                    viewModel.onEdit(presentationModel)
                    true
                }

                R.id.action_delete -> {
                    confirmToDelete(presentationModel)
                    true
                }

                else -> false
            }
        }

        popup.show()
    }

    private fun confirmToDelete(model: ProblemCategoryPresentationModel) {
        if (model.isDefault) {
            showSnackbar(R.string.action_no_delete_default_category)
            return
        }

        showConfirmDialog(
            R.string.delete_problem_category,
            R.string.delete_problem_category_message,
            negativeAction = Pair(R.string.action_delete_move_to) {
                viewModel.onDelete(model, DataDeleteType.MoveToAnother())

            },
            positiveAction = Pair(R.string.action_delete_delete_data) {
                viewModel.onDelete(model, DataDeleteType.DeleteData)
            }
        )
    }
}
