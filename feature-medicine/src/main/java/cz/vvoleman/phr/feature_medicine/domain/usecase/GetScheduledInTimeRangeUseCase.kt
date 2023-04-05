package cz.vvoleman.phr.feature_medicine.domain.usecase

import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.base.domain.usecase.BackgroundExecutingUseCase
import cz.vvoleman.phr.feature_medicine.domain.model.schedule.MedicineScheduleDomainModel
import cz.vvoleman.phr.feature_medicine.domain.model.timeline.SchedulesInRangeRequestDomainModel
import cz.vvoleman.phr.feature_medicine.domain.repository.timeline.GetScheduledInTimeRangeRepository

class GetScheduledInTimeRangeUseCase(
    private val repository: GetScheduledInTimeRangeRepository,
    coroutineContextProvider: CoroutineContextProvider
) : BackgroundExecutingUseCase<SchedulesInRangeRequestDomainModel, List<MedicineScheduleDomainModel>>(coroutineContextProvider) {

    override suspend fun executeInBackground(request: SchedulesInRangeRequestDomainModel): List<MedicineScheduleDomainModel> {
        return repository.getScheduledInTimeRange(request)
    }
}