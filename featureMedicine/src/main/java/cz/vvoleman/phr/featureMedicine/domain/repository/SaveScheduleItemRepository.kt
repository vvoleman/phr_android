package cz.vvoleman.phr.featureMedicine.domain.repository

import cz.vvoleman.phr.featureMedicine.domain.model.schedule.ScheduleItemDomainModel
import cz.vvoleman.phr.featureMedicine.domain.model.schedule.save.SaveScheduleItemDomainModel

interface SaveScheduleItemRepository {

    suspend fun saveScheduleItem(scheduleItem: SaveScheduleItemDomainModel, scheduleId: String): ScheduleItemDomainModel
}
