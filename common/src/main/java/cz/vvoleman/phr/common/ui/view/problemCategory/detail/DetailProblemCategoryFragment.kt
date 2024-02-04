package cz.vvoleman.phr.common.ui.view.problemCategory.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import cz.vvoleman.phr.base.presentation.viewmodel.BaseViewModel
import cz.vvoleman.phr.base.ui.mapper.DestinationUiMapper
import cz.vvoleman.phr.base.ui.mapper.ViewStateBinder
import cz.vvoleman.phr.base.ui.view.BaseFragment
import cz.vvoleman.phr.common.presentation.model.problemCategory.detail.DetailProblemCategoryNotification
import cz.vvoleman.phr.common.presentation.model.problemCategory.detail.DetailProblemCategoryViewState
import cz.vvoleman.phr.common.presentation.viewmodel.problemCategory.DetailProblemCategoryViewModel
import cz.vvoleman.phr.common.ui.mapper.problemCategory.destination.DetailProblemCategoryDestinationUiMapper
import cz.vvoleman.phr.common_datasource.databinding.FragmentDetailProblemCategoryBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailProblemCategoryFragment :
    BaseFragment<DetailProblemCategoryViewState, DetailProblemCategoryNotification, FragmentDetailProblemCategoryBinding>() {

    override val viewModel: DetailProblemCategoryViewModel by viewModels()

    @Inject
    override lateinit var destinationMapper: DetailProblemCategoryDestinationUiMapper

    @Inject
    override lateinit var viewStateBinder: ViewStateBinder<DetailProblemCategoryViewState, FragmentDetailProblemCategoryBinding>

    override fun setupBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentDetailProblemCategoryBinding {
        return FragmentDetailProblemCategoryBinding.inflate(inflater, container, false)
    }

    override fun handleNotification(notification: DetailProblemCategoryNotification) {
        when (notification) {
            else -> {
                showSnackbar("Not yet implemented: $notification")
            }
        }
    }
}
