package cz.vvoleman.phr.featureMedicalRecord.domain.usecase.addEdit

import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.base.domain.usecase.BackgroundExecutingUseCase
import cz.vvoleman.phr.featureMedicalRecord.domain.model.DiagnoseDomainModel
import cz.vvoleman.phr.featureMedicalRecord.domain.model.addEdit.SearchRequestDomainModel
import cz.vvoleman.phr.featureMedicalRecord.domain.repository.addEdit.SearchDiagnoseRepository

class SearchDiagnoseUseCase(
    private val searchDiagnoseRepository: SearchDiagnoseRepository,
    coroutineContextProvider: CoroutineContextProvider
) : BackgroundExecutingUseCase<SearchRequestDomainModel, List<DiagnoseDomainModel>>(coroutineContextProvider) {

    override suspend fun executeInBackground(request: SearchRequestDomainModel): List<DiagnoseDomainModel> {
        return searchDiagnoseRepository.searchDiagnose(request.query, request.page)
    }
}
