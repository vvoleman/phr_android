package cz.vvoleman.phr.common.presentation.viewmodel.problemCategory

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import cz.vvoleman.phr.base.domain.eventBus.EventBusChannel
import cz.vvoleman.phr.base.presentation.viewmodel.BaseViewModel
import cz.vvoleman.phr.base.presentation.viewmodel.usecase.UseCaseExecutorProvider
import cz.vvoleman.phr.common.domain.repository.problemCategory.GetProblemCategoryByIdRepository
import cz.vvoleman.phr.common.domain.usecase.patient.GetSelectedPatientUseCase
import cz.vvoleman.phr.common.presentation.event.problemCategory.ExportProblemCategoryEvent
import cz.vvoleman.phr.common.presentation.event.problemCategory.GetProblemCategoryDetailSectionEvent
import cz.vvoleman.phr.common.presentation.mapper.PatientPresentationModelToDomainMapper
import cz.vvoleman.phr.common.presentation.mapper.problemCategory.ProblemCategoryPresentationModelToDomainMapper
import cz.vvoleman.phr.common.presentation.model.patient.PatientPresentationModel
import cz.vvoleman.phr.common.presentation.model.problemCategory.ProblemCategoryPresentationModel
import cz.vvoleman.phr.common.presentation.model.problemCategory.detail.DetailProblemCategoryNotification
import cz.vvoleman.phr.common.presentation.model.problemCategory.detail.DetailProblemCategoryViewState
import cz.vvoleman.phr.common.presentation.model.problemCategory.export.ProblemCategoryParams
import cz.vvoleman.phr.common.ui.export.usecase.DocumentPage
import cz.vvoleman.phr.common.ui.view.problemCategory.detail.groupie.SectionContainer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailProblemCategoryViewModel @Inject constructor(
    private val detailSectionEventBus: EventBusChannel<GetProblemCategoryDetailSectionEvent, List<SectionContainer>>,
    private val exportProblemCategoryEventBus: EventBusChannel<ExportProblemCategoryEvent, List<DocumentPage>>,
    private val getSelectedPatientUseCase: GetSelectedPatientUseCase,
    private val getProblemCategoryByIdRepository: GetProblemCategoryByIdRepository,
    private val problemCategoryMapper: ProblemCategoryPresentationModelToDomainMapper,
    private val patientMapper: PatientPresentationModelToDomainMapper,
    savedStateHandle: SavedStateHandle,
    useCaseExecutorProvider: UseCaseExecutorProvider,
) : BaseViewModel<DetailProblemCategoryViewState, DetailProblemCategoryNotification>(
    savedStateHandle, useCaseExecutorProvider
) {

    override val TAG = "DetailProblemCategoryViewModel"

    override suspend fun initState(): DetailProblemCategoryViewState {
        val patient = getSelectedPatient()
        val problemCategory = getProblemCategory()
        val sections = getDetailSection(problemCategory)
        val createdAt = problemCategory.createdAt
        val updatedAt = sections.mapNotNull { it.updatedAt }.maxOrNull()

        return DetailProblemCategoryViewState(
            patient = patient,
            problemCategory = problemCategory,
            sections = sections,
            createdAt = createdAt.toLocalDate(),
            updatedAt = updatedAt
        )
    }

    private suspend fun getProblemCategory(): ProblemCategoryPresentationModel {
        val id = savedStateHandle.get<String>("problemCategoryId")
        requireNotNull(id) { "Problem category id is required" }

        val problemCategory = getProblemCategoryByIdRepository.getProblemCategoryById(id)
        requireNotNull(problemCategory) { "Problem category not found" }

        return problemCategoryMapper.toPresentation(problemCategory)
    }

    private suspend fun getDetailSection(problemCategory: ProblemCategoryPresentationModel): List<SectionContainer> {
        val event = GetProblemCategoryDetailSectionEvent(problemCategory)
        val results = detailSectionEventBus.pushEvent(event)

        return results.flatten()
    }

    private suspend fun getSelectedPatient(): PatientPresentationModel {
        return getSelectedPatientUseCase.execute(null).first()
            .let { patientMapper.toPresentation(it) }
    }

    fun onExport() = viewModelScope.launch{
        val event = ExportProblemCategoryEvent(
            problemCategory = currentViewState.problemCategory,
        )
        val results = exportProblemCategoryEventBus
            .pushEvent(event)
            .flatten()

        if (results.isEmpty()) {
            notify(DetailProblemCategoryNotification.ExportEmpty)
            return@launch
        }

        val params = ProblemCategoryParams(
            problemCategory = currentViewState.problemCategory,
            patient = currentViewState.patient
        )

        notify(DetailProblemCategoryNotification.ExportPdf(params, results))
    }
}
