package cz.vvoleman.phr.common.presentation.viewmodel.problemCategory

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import cz.vvoleman.phr.base.presentation.viewmodel.BaseViewModel
import cz.vvoleman.phr.base.presentation.viewmodel.usecase.UseCaseExecutorProvider
import cz.vvoleman.phr.common.domain.model.problemCategory.request.SaveProblemCategoryRequest
import cz.vvoleman.phr.common.domain.repository.problemCategory.GetProblemCategoryByIdRepository
import cz.vvoleman.phr.common.domain.usecase.patient.GetSelectedPatientUseCase
import cz.vvoleman.phr.common.domain.usecase.problemCategory.SaveProblemCategoryUseCase
import cz.vvoleman.phr.common.presentation.factory.ColorFactory
import cz.vvoleman.phr.common.presentation.mapper.PatientPresentationModelToDomainMapper
import cz.vvoleman.phr.common.presentation.mapper.problemCategory.ProblemCategoryPresentationModelToDomainMapper
import cz.vvoleman.phr.common.presentation.model.patient.PatientPresentationModel
import cz.vvoleman.phr.common.presentation.model.problemCategory.ProblemCategoryPresentationModel
import cz.vvoleman.phr.common.presentation.model.problemCategory.addEdit.AddEditProblemCategoryDestination
import cz.vvoleman.phr.common.presentation.model.problemCategory.addEdit.AddEditProblemCategoryNotification
import cz.vvoleman.phr.common.presentation.model.problemCategory.addEdit.AddEditProblemCategoryViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditProblemCategoryViewModel @Inject constructor(
    private val getSelectedPatientUseCase: GetSelectedPatientUseCase,
    private val saveProblemCategoryUseCase: SaveProblemCategoryUseCase,
    private val getProblemCategoryByIdRepository: GetProblemCategoryByIdRepository,
    private val patientMapper: PatientPresentationModelToDomainMapper,
    private val problemCategoryMapper: ProblemCategoryPresentationModelToDomainMapper,
    private val colorFactory: ColorFactory,
    savedStateHandle: SavedStateHandle,
    useCaseExecutorProvider: UseCaseExecutorProvider,
) : BaseViewModel<AddEditProblemCategoryViewState, AddEditProblemCategoryNotification>(
    savedStateHandle,
    useCaseExecutorProvider
) {

    override val TAG = "AddEditProblemCategoryViewModel"

    override suspend fun initState(): AddEditProblemCategoryViewState {
        val patient = getSelectedPatient()
        val categoryId = savedStateHandle.get<String>("problemCategoryId")
        val category = categoryId?.let { getProblemCategory(it) }
        val allColors = colorFactory.getStandardColors()

        return AddEditProblemCategoryViewState(
            problemCategoryId = categoryId,
            patient = patient,
            createdAt = category?.createdAt,
            name = category?.name,
            selectedColor = category?.color,
            colorList = allColors,
        )
    }

    private suspend fun getSelectedPatient(): PatientPresentationModel {
        val patient = getSelectedPatientUseCase.execute(null).first()
        return patientMapper.toPresentation(patient)
    }

    private suspend fun getProblemCategory(id: String): ProblemCategoryPresentationModel? {
        val category = getProblemCategoryByIdRepository.getProblemCategoryById(id)

        return category?.let { problemCategoryMapper.toPresentation(it) }
    }

    fun onColorSelected(color: String?) {
        updateViewState(currentViewState.copy(selectedColor = color))
    }

    fun onNameChanged(name: String?) {
        updateViewState(currentViewState.copy(name = name))
    }

    fun onSave() = viewModelScope.launch {
        val missingFields = currentViewState.missingFields

        Log.d(TAG, currentViewState.toString())

        if (missingFields.isNotEmpty()) {
            notify(AddEditProblemCategoryNotification.MissingFields(missingFields))
            return@launch
        }

        val request = SaveProblemCategoryRequest(
            id = currentViewState.problemCategoryId,
            patientId = currentViewState.patient.id,
            name = currentViewState.name!!,
            color = currentViewState.selectedColor!!,
            createdAt = currentViewState.createdAt
        )
        val savedId = saveProblemCategoryUseCase.executeInBackground(request)

        navigateTo(AddEditProblemCategoryDestination.Saved(savedId))
    }
}
