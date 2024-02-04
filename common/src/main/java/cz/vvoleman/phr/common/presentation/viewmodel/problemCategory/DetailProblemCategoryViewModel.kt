package cz.vvoleman.phr.common.presentation.viewmodel.problemCategory

import androidx.lifecycle.SavedStateHandle
import cz.vvoleman.phr.base.presentation.viewmodel.BaseViewModel
import cz.vvoleman.phr.base.presentation.viewmodel.usecase.UseCaseExecutorProvider
import cz.vvoleman.phr.common.domain.repository.problemCategory.GetProblemCategoryByIdRepository
import cz.vvoleman.phr.common.presentation.mapper.problemCategory.ProblemCategoryPresentationModelToDomainMapper
import cz.vvoleman.phr.common.presentation.model.problemCategory.ProblemCategoryPresentationModel
import cz.vvoleman.phr.common.presentation.model.problemCategory.detail.DetailProblemCategoryNotification
import cz.vvoleman.phr.common.presentation.model.problemCategory.detail.DetailProblemCategoryViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailProblemCategoryViewModel @Inject constructor(
    private val getProblemCategoryByIdRepository: GetProblemCategoryByIdRepository,
    private val problemCategoryMapper: ProblemCategoryPresentationModelToDomainMapper,
    savedStateHandle: SavedStateHandle,
    useCaseExecutorProvider: UseCaseExecutorProvider,
) : BaseViewModel<DetailProblemCategoryViewState, DetailProblemCategoryNotification>(
    savedStateHandle, useCaseExecutorProvider
) {

    override val TAG = "DetailProblemCategoryViewModel"

    override suspend fun initState(): DetailProblemCategoryViewState {
        val problemCategory = getProblemCategory()
        
        return DetailProblemCategoryViewState(
            problemCategory = problemCategory
        )
    }

    private suspend fun getProblemCategory(): ProblemCategoryPresentationModel {
        val id = savedStateHandle.get<String>("problemCategoryId")
        requireNotNull(id) { "Problem category id is required" }

        val problemCategory = getProblemCategoryByIdRepository.getProblemCategoryById(id)
        requireNotNull(problemCategory) { "Problem category not found" }

        return problemCategoryMapper.toPresentation(problemCategory)
    }
}
