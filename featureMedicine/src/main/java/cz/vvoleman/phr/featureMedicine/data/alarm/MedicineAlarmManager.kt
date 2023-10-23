package cz.vvoleman.phr.featureMedicine.data.alarm

import android.util.Log
import cz.vvoleman.phr.common.data.alarm.ModuleAlarmManager
import cz.vvoleman.phr.featureMedicine.data.repository.AlarmRepository

class MedicineAlarmManager (
    private val alarmRepository: AlarmRepository
) : ModuleAlarmManager() {

    override suspend fun initAlarms() {
        Log.d(TAG, "initAlarms: ")
        val activeMedicineSchedules = alarmRepository.getActiveMedicineSchedules()

        activeMedicineSchedules.forEach {
            val isScheduled = alarmRepository.scheduleMedicine(it)
            Log.d(TAG, "scheduled: $isScheduled, $it")
        }
    }

    companion object {
        const val TAG = "MedicineAlarmManager"
    }
}