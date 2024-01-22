package cz.vvoleman.phr.featureMeasurement.data.alarm

import cz.vvoleman.phr.common.data.alarm.BootReceiver
import cz.vvoleman.phr.common.data.alarm.ModuleAlarmManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MeasurementBootReceiver : BootReceiver() {

    @Inject
    lateinit var measurementAlarmManager: MeasurementAlarmManager

    override fun setupManagers(): List<ModuleAlarmManager> {
        return listOf(measurementAlarmManager)
    }
}
