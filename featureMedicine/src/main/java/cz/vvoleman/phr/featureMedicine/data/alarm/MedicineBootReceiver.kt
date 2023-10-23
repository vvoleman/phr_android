package cz.vvoleman.phr.featureMedicine.data.alarm

import cz.vvoleman.phr.common.data.alarm.BootReceiver
import cz.vvoleman.phr.common.data.alarm.ModuleAlarmManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MedicineBootReceiver : BootReceiver() {

    @Inject
    lateinit var medicineAlarmManager: MedicineAlarmManager

    override fun setupManagers(): List<ModuleAlarmManager> {
        return listOf(medicineAlarmManager)
    }
}