package cz.vvoleman.phr.featureEvent.data.alarm.notification.event

import android.app.NotificationManager
import android.content.Context
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.navigation.NavDeepLinkBuilder
import cz.vvoleman.phr.common.utils.localizedDiff
import cz.vvoleman.phr.featureEvent.R
import cz.vvoleman.phr.featureEvent.data.alarm.receiver.event.EventAlarmContent
import cz.vvoleman.phr.featureEvent.data.alarm.receiver.event.EventAlarmReceiver
import cz.vvoleman.phr.featureEvent.domain.model.core.EventDomainModel
import java.time.LocalDateTime

class EventNotificationService(
    private val context: Context
) {

    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    fun showNotification(event: EventDomainModel, content: EventAlarmContent) {
        val args = Bundle().apply {
            putString("eventId", event.id)
        }

        val pendingIntent = NavDeepLinkBuilder(context)
            .setGraph(R.navigation.nav_event)
            .setDestination(R.id.listEventFragment)
            .setArguments(args)
            .createPendingIntent()

        val now = LocalDateTime.now()

        val notification = NotificationCompat.Builder(context, EventAlarmReceiver.CHANNEL_ID)
            .setSmallIcon(cz.vvoleman.phr.common_datasource.R.drawable.ic_health)
            .setContentTitle(context.resources.getString(R.string.notification_event_title))
            .setContentText(
                context.resources.getString(
                    R.string.notification_event_text,
                    event.name,
                    now.localizedDiff(event.startAt)
                )
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setColor(context.getColor(cz.vvoleman.phr.base.R.color.color_primary))
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(event.id.hashCode() + content.triggerAt.toInt(), notification)
    }
}
