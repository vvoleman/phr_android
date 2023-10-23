package cz.vvoleman.phr.featureMedicine.data.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Parcelable
import android.util.Log
import cz.vvoleman.phr.common.data.alarm.AlarmItem
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MedicineAlarmReceiver : BroadcastReceiver() {

    //    @Inject
//    lateinit var scheduleRepository: ScheduleRepository


    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d(TAG, "onReceive: ${intent?.action}")
        val scope = CoroutineScope(Job() + Dispatchers.Main.immediate)
        val content = intent?.getParcelableExtra<Parcelable>(AlarmItem.CONTENT_KEY) ?: return
        val testContent = content as MedicineAlarmContent

        if (context == null) {
            return
        }

        scope.launch {
//            val medicineSchedule = scheduleRepository.getMedicineScheduleById(testContent.medicineScheduleId)
//
//            if (medicineSchedule == null) {
//                Log.d(TAG, "onReceive: medicineSchedule is null")
//                return@launch
//            }
//
//            Log.d(TAG, "Received schedule for: ${medicineSchedule.medicine.name}")
//
//            Log.d(TAG, "Received: $testContent")
            val service = CounterNotificationService(context)
            service.showNotification(++Counter.value)
        }
    }

    companion object {
        const val TAG = "MedicineAlarmReceiver"
        const val CHANNEL_ID = "medicine-alarm"
    }

}