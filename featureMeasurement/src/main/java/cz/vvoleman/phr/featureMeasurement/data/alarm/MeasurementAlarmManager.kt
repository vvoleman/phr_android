package cz.vvoleman.phr.featureMeasurement.data.alarm

import android.util.Log
import cz.vvoleman.phr.common.data.alarm.ModuleAlarmManager
import cz.vvoleman.phr.featureMeasurement.data.repository.AlarmRepository

class MeasurementAlarmManager(
    private val alarmRepository: AlarmRepository
) : ModuleAlarmManager() {

    override suspend fun initAlarms() {
        Log.d(TAG, "initAlarms: ")
        val activeMeasurementSchedules = alarmRepository.getActiveMeasurementGroups()

        activeMeasurementSchedules.forEach {
            val isScheduled = alarmRepository.scheduleMeasurementGroup(it)
            Log.d(TAG, "scheduled: $isScheduled, $it")
        }
    }

    companion object {
        const val TAG = "MeasurementAlarmManager"
    }
}
