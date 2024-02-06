package cz.vvoleman.phr.featureEvent.data.alarm

import cz.vvoleman.phr.common.data.alarm.BootReceiver
import cz.vvoleman.phr.common.data.alarm.ModuleAlarmManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class EventBootReceiver : BootReceiver() {

    @Inject
    lateinit var eventAlarmManager: EventAlarmManager

    override fun setupManagers(): List<ModuleAlarmManager> {
        return listOf(eventAlarmManager)
    }
}
