package cz.vvoleman.phr.featureMedicine.domain.repository.timeline

import cz.vvoleman.phr.featureMedicine.domain.model.schedule.MedicineScheduleDomainModel
import cz.vvoleman.phr.featureMedicine.domain.model.timeline.SchedulesInRangeRequest

interface GetScheduledInTimeRangeRepository {

    suspend fun getScheduledInTimeRange(request: SchedulesInRangeRequest): List<MedicineScheduleDomainModel>
}
