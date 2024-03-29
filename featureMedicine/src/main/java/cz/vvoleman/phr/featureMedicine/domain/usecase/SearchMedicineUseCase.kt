package cz.vvoleman.phr.featureMedicine.domain.usecase

import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.base.domain.usecase.BackgroundExecutingUseCase
import cz.vvoleman.phr.featureMedicine.domain.model.SearchMedicineRequestDomainModel
import cz.vvoleman.phr.featureMedicine.domain.model.medicine.MedicineDomainModel
import cz.vvoleman.phr.featureMedicine.domain.repository.SearchMedicineRepository

class SearchMedicineUseCase(
    private val searchMedicineRepository: SearchMedicineRepository,
    coroutineContextProvider: CoroutineContextProvider
) : BackgroundExecutingUseCase<SearchMedicineRequestDomainModel, List<MedicineDomainModel>>(coroutineContextProvider) {

    override suspend fun executeInBackground(request: SearchMedicineRequestDomainModel): List<MedicineDomainModel> {
        return searchMedicineRepository.searchMedicine(request.query, request.page)
    }
}
