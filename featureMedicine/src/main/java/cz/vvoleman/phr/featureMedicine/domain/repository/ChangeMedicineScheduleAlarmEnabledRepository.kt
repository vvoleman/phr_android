package cz.vvoleman.phr.featureMedicine.domain.repository

interface ChangeMedicineScheduleAlarmEnabledRepository {

    suspend fun changeMedicineScheduleAlarmEnabled(medicineScheduleId: String, enabled: Boolean): Boolean

}
