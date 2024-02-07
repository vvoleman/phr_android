package cz.vvoleman.phr.featureMeasurement.data.alarm.receiver.measurementGroup

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import cz.vvoleman.phr.common.data.alarm.AlarmItem
import cz.vvoleman.phr.featureMeasurement.data.alarm.notification.measurementGroup.MeasurementGroupNotificationService
import cz.vvoleman.phr.featureMeasurement.domain.repository.GetMeasurementGroupRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset
import javax.inject.Inject
import kotlin.math.floor

@AndroidEntryPoint
class MeasurementGroupAlarmReceiver : BroadcastReceiver() {

    @Inject
    lateinit var repository: GetMeasurementGroupRepository

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d(TAG, "onReceive: ${intent?.action}")
        val scope = CoroutineScope(Job() + Dispatchers.Main.immediate)
        val content = intent?.getParcelableExtra<MeasurementGroupAlarmContent>(AlarmItem.CONTENT_KEY) ?: return

        if (context == null) {
            return
        }

        // Check if today is day for triggering
        val today = LocalDate.now()
        if (!content.alarmDays.contains(today.dayOfWeek)) {
            Log.d(TAG, "onReceive: today is not day for triggering ${content.measurementGroupId}")
            return
        }

        scope.launch {
            val measurementGroup = repository.getMeasurementGroup(content.measurementGroupId)

            val timestamp = LocalDateTime.ofEpochSecond(
                floor((content.triggerAt).toDouble()).toLong(),
                0,
                ZoneOffset.UTC
            )
            Log.d(
                TAG,
                "onReceive: measurementGroup: ${
                    LocalDateTime.ofEpochSecond(
                        floor((content.triggerAt).toDouble()).toLong(),
                        0,
                        ZoneOffset.UTC
                    )
                }"
            )
            if (measurementGroup == null) {
                Log.d(TAG, "onReceive: measurementGroup is null")
                return@launch
            }

            val service = MeasurementGroupNotificationService(context)
            service.showNotification(measurementGroup, timestamp)
        }
    }

    companion object {
        const val TAG = "MeasurementGroupAlarmReceiver"
        const val CHANNEL_ID = "measurement-group-alarm"
    }
}
