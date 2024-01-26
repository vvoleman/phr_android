package cz.vvoleman.phr.featureMeasurement.domain.usecase.addEditEntry

import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.base.domain.usecase.BackgroundExecutingUseCase
import cz.vvoleman.phr.featureMeasurement.domain.model.addEditEntry.GetEntryByIdRequest
import cz.vvoleman.phr.featureMeasurement.domain.model.core.MeasurementGroupEntryDomainModel
import cz.vvoleman.phr.featureMeasurement.domain.repository.GetEntryByIdRepository

class GetEntryByIdUseCase(
    private val getEntryByIdRepository: GetEntryByIdRepository,
    coroutineContextProvider: CoroutineContextProvider
) : BackgroundExecutingUseCase<GetEntryByIdRequest, List<MeasurementGroupEntryDomainModel>>(coroutineContextProvider) {

    override suspend fun executeInBackground(request: GetEntryByIdRequest): List<MeasurementGroupEntryDomainModel> {
        if (request.entryId != null) {
            return getEntryByIdRepository.getEntryById(request.entryId)
        }

        return emptyList()
    }
}
