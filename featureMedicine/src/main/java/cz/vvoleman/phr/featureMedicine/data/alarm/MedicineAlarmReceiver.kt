package cz.vvoleman.phr.featureMedicine.data.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import cz.vvoleman.phr.common.data.alarm.AlarmItem
import cz.vvoleman.phr.featureMedicine.domain.repository.GetMedicineScheduleByIdRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneOffset
import javax.inject.Inject

@AndroidEntryPoint
class MedicineAlarmReceiver : BroadcastReceiver() {

    @Inject
    lateinit var scheduleRepository: GetMedicineScheduleByIdRepository

    override fun onReceive(context: Context?, intent: Intent?) {
        val scope = CoroutineScope(Job() + Dispatchers.Main.immediate)
        val content = intent?.getParcelableExtra<MedicineAlarmContent>(AlarmItem.CONTENT_KEY) ?: return

        if (context == null) {
            return
        }

        scope.launch {
            val medicineSchedule = scheduleRepository.getMedicineScheduleById(content.medicineScheduleId)

            if (medicineSchedule == null) {
                Log.d(TAG, "onReceive: medicineSchedule is null")
                return@launch
            }
            val service = MedicineNotificationService(context)
            val triggerAt = LocalDateTime.ofEpochSecond(content.triggerAt, 0, ZoneOffset.UTC)
            service.showNotification(medicineSchedule, triggerAt)
        }
    }

    companion object {
        const val TAG = "MedicineAlarmReceiver"
        const val CHANNEL_ID = "medicine-alarm"
    }
}
