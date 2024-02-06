package cz.vvoleman.phr.featureEvent.data.alarm

import android.util.Log
import cz.vvoleman.phr.common.data.alarm.ModuleAlarmManager
import cz.vvoleman.phr.featureEvent.data.repository.AlarmRepository

class EventAlarmManager(
    private val alarmRepository: AlarmRepository
) : ModuleAlarmManager() {

    override suspend fun initAlarms() {
        Log.d(TAG, "initAlarms: ")
        val activeEvents = alarmRepository.getActiveEvents()

        activeEvents.forEach {
            val isScheduled = alarmRepository.scheduleEventAlarm(it)
            Log.d(TAG, "scheduled: $isScheduled, $it")
        }
    }

    companion object {
        const val TAG = "EventAlarmManager"
    }
}
