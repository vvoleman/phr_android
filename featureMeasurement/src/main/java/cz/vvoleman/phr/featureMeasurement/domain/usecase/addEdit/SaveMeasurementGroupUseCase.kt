package cz.vvoleman.phr.featureMeasurement.domain.usecase.addEdit

import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.base.domain.usecase.BackgroundExecutingUseCase
import cz.vvoleman.phr.featureMeasurement.domain.model.addEdit.SaveMeasurementGroupDomainModel
import cz.vvoleman.phr.featureMeasurement.domain.repository.SaveMeasurementGroupRepository

class SaveMeasurementGroupUseCase(
    private val saveMeasurementGroupRepository: SaveMeasurementGroupRepository,
    coroutineContextProvider: CoroutineContextProvider,
) : BackgroundExecutingUseCase<SaveMeasurementGroupDomainModel, String?>(coroutineContextProvider) {
    override suspend fun executeInBackground(request: SaveMeasurementGroupDomainModel): String? {
        return saveMeasurementGroupRepository.saveMeasurementGroup(request)?.id
    }
}
