package cz.vvoleman.phr.featureEvent.data.alarm.receiver.event

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import cz.vvoleman.phr.common.data.alarm.AlarmItem
import cz.vvoleman.phr.featureEvent.data.alarm.notification.event.EventNotificationService
import cz.vvoleman.phr.featureEvent.domain.repository.GetEventByIdRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class EventAlarmReceiver : BroadcastReceiver() {

    @Inject
    lateinit var repository: GetEventByIdRepository

    override fun onReceive(context: Context?, intent: Intent?) {
        val scope = CoroutineScope(Job() + Dispatchers.Main.immediate)
        val content = intent?.getParcelableExtra<EventAlarmContent>(AlarmItem.CONTENT_KEY) ?: return

        if (context == null) {
            return
        }

        scope.launch {
            val event = repository.getEventById(content.eventId)
            if (event == null) {
                Log.e(TAG, "onReceive: event is null (id: ${content.eventId})")
                return@launch
            }

            val service = EventNotificationService(context)
            service.showNotification(event)
        }
    }

    companion object {
        const val TAG = "EventAlarmReceiver"
        const val CHANNEL_ID = "event-alarm"
    }
}
