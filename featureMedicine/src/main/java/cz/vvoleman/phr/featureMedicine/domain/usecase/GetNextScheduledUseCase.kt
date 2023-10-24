package cz.vvoleman.phr.featureMedicine.domain.usecase

import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.base.domain.usecase.BackgroundExecutingUseCase
import cz.vvoleman.phr.featureMedicine.domain.model.schedule.MedicineScheduleDomainModel
import cz.vvoleman.phr.featureMedicine.domain.model.timeline.NextScheduledRequestDomainModel
import cz.vvoleman.phr.featureMedicine.domain.repository.timeline.GetSchedulesByPatientRepository

class GetNextScheduledUseCase(
    private val getSchedulesByPatientRepository: GetSchedulesByPatientRepository,
    coroutineContextProvider: CoroutineContextProvider,
) : BackgroundExecutingUseCase<NextScheduledRequestDomainModel, List<MedicineScheduleDomainModel>>(coroutineContextProvider) {

    override suspend fun executeInBackground(request: NextScheduledRequestDomainModel): List<MedicineScheduleDomainModel> {
        return listOf()
    }
}