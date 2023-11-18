package cz.vvoleman.phr.featureMedicine.domain.repository

import cz.vvoleman.phr.featureMedicine.domain.model.schedule.ScheduleItemDomainModel

interface SaveScheduleItemRepository {

    suspend fun saveScheduleItem(scheduleItem: ScheduleItemDomainModel, scheduleId: String): ScheduleItemDomainModel
}
