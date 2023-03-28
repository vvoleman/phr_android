package cz.vvoleman.phr.feature_medicalrecord.domain.usecase.add_edit

import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.base.domain.usecase.BackgroundExecutingUseCase
import cz.vvoleman.phr.feature_medicalrecord.domain.model.DiagnoseDomainModel
import cz.vvoleman.phr.feature_medicalrecord.domain.model.add_edit.SearchRequestDomainModel
import cz.vvoleman.phr.feature_medicalrecord.domain.repository.add_edit.SearchDiagnoseRepository

class SearchDiagnoseUseCase(
    private val searchDiagnoseRepository: SearchDiagnoseRepository,
    coroutineContextProvider: CoroutineContextProvider
) : BackgroundExecutingUseCase<SearchRequestDomainModel, List<DiagnoseDomainModel>>(coroutineContextProvider) {

    override suspend fun executeInBackground(request: SearchRequestDomainModel): List<DiagnoseDomainModel> {
        return searchDiagnoseRepository.searchDiagnose(request.query, request.page)
    }
}