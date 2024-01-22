package cz.vvoleman.featureMeasurement.domain.usecase.addEdit

import cz.vvoleman.featureMeasurement.domain.model.addEdit.SaveMeasurementGroupDomainModel
import cz.vvoleman.featureMeasurement.domain.repository.SaveMeasurementGroupRepository
import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.base.domain.usecase.BackgroundExecutingUseCase

class SaveMeasurementGroupUseCase(
    private val saveMeasurementGroupRepository: SaveMeasurementGroupRepository,
    coroutineContextProvider: CoroutineContextProvider,
) : BackgroundExecutingUseCase<SaveMeasurementGroupDomainModel, String?>(coroutineContextProvider) {
    override suspend fun executeInBackground(request: SaveMeasurementGroupDomainModel): String? {
        return saveMeasurementGroupRepository.saveMeasurementGroup(request)?.id
    }
}
