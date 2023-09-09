package cz.vvoleman.phr.feature_medicine.domain.repository.timeline

import cz.vvoleman.phr.feature_medicine.domain.model.schedule.MedicineScheduleDomainModel
import cz.vvoleman.phr.feature_medicine.domain.model.timeline.SchedulesInRangeRequestDomainModel

interface GetScheduledInTimeRangeRepository {

    suspend fun getScheduledInTimeRange(request: SchedulesInRangeRequestDomainModel): List<MedicineScheduleDomainModel>
}
