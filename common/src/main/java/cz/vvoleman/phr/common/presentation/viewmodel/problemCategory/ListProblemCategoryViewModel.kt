package cz.vvoleman.phr.common.presentation.viewmodel.problemCategory

import androidx.lifecycle.SavedStateHandle
import cz.vvoleman.phr.base.presentation.viewmodel.BaseViewModel
import cz.vvoleman.phr.base.presentation.viewmodel.usecase.UseCaseExecutorProvider
import cz.vvoleman.phr.common.domain.model.problemCategory.request.GetProblemCategoriesRequest
import cz.vvoleman.phr.common.domain.usecase.patient.GetSelectedPatientUseCase
import cz.vvoleman.phr.common.domain.usecase.problemCategory.GetProblemCategoriesUseCase
import cz.vvoleman.phr.common.presentation.mapper.PatientPresentationModelToDomainMapper
import cz.vvoleman.phr.common.presentation.mapper.problemCategory.ProblemCategoryWithInfoPresentationModelToDomainMapper
import cz.vvoleman.phr.common.presentation.model.patient.PatientPresentationModel
import cz.vvoleman.phr.common.presentation.model.problemCategory.ProblemCategoryPresentationModel
import cz.vvoleman.phr.common.presentation.model.problemCategory.ProblemCategoryWithInfoPresentationModel
import cz.vvoleman.phr.common.presentation.model.problemCategory.list.ListProblemCategoryDestination
import cz.vvoleman.phr.common.presentation.model.problemCategory.list.ListProblemCategoryNotification
import cz.vvoleman.phr.common.presentation.model.problemCategory.list.ListProblemCategoryViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@HiltViewModel
class ListProblemCategoryViewModel @Inject constructor(
    private val getProblemCategoriesUseCase: GetProblemCategoriesUseCase,
    private val getSelectedPatientUseCase: GetSelectedPatientUseCase,
    private val patientMapper: PatientPresentationModelToDomainMapper,
    private val categoryWithInfoMapper: ProblemCategoryWithInfoPresentationModelToDomainMapper,
    savedStateHandle: SavedStateHandle,
    useCaseExecutorProvider: UseCaseExecutorProvider
) : BaseViewModel<ListProblemCategoryViewState, ListProblemCategoryNotification>(
    savedStateHandle, useCaseExecutorProvider
) {

    override val TAG = "ListProblemCategoryViewModel"

    override suspend fun initState(): ListProblemCategoryViewState {
        val patient = getSelectedPatient()
        val categories = getProblemCategories(patient)
        return ListProblemCategoryViewState(
            patient = patient,
            problemCategories = categories
        )
    }

    fun onCreate() {
        navigateTo(ListProblemCategoryDestination.AddProblemCategory)
    }

    fun onEdit(item: ProblemCategoryPresentationModel) {
        navigateTo(ListProblemCategoryDestination.EditProblemCategory(item.id))
    }

    fun onDelete(item: ProblemCategoryPresentationModel) {
    }

    fun onDetail(item: ProblemCategoryPresentationModel) {
        navigateTo(ListProblemCategoryDestination.OpenDetail(item.id))
    }

    private suspend fun getSelectedPatient(): PatientPresentationModel {
        val patient = getSelectedPatientUseCase.execute(null).first()
        return patientMapper.toPresentation(patient)
    }

    private suspend fun getProblemCategories(patient: PatientPresentationModel): List<ProblemCategoryWithInfoPresentationModel> {
        val request = GetProblemCategoriesRequest(patient.id)
        return getProblemCategoriesUseCase.executeInBackground(request).map {
            categoryWithInfoMapper.toPresentation(it)
        }
    }

}
