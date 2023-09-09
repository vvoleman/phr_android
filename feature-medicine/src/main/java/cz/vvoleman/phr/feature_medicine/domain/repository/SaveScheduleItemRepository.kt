package cz.vvoleman.phr.feature_medicine.domain.repository

import cz.vvoleman.phr.feature_medicine.domain.model.schedule.ScheduleItemDomainModel
import cz.vvoleman.phr.feature_medicine.domain.model.schedule.save.SaveScheduleItemDomainModel

interface SaveScheduleItemRepository {

    suspend fun saveScheduleItem(scheduleItem: SaveScheduleItemDomainModel, scheduleId: String): ScheduleItemDomainModel
}
