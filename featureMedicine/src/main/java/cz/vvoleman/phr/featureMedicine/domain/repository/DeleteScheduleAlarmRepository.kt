package cz.vvoleman.phr.featureMedicine.domain.repository

import cz.vvoleman.phr.featureMedicine.domain.model.schedule.MedicineScheduleDomainModel

interface DeleteScheduleAlarmRepository {

    suspend fun deleteScheduleAlarm(medicineSchedule: MedicineScheduleDomainModel): Boolean

}