package cz.vvoleman.phr.featureMeasurement.data.alarm.notification.measurementGroup

import android.app.NotificationManager
import android.content.Context
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.navigation.NavDeepLinkBuilder
import cz.vvoleman.featureMeasurement.R
import cz.vvoleman.phr.featureMeasurement.data.alarm.receiver.measurementGroup.MeasurementGroupAlarmReceiver
import cz.vvoleman.phr.featureMeasurement.domain.model.core.MeasurementGroupDomainModel

class MeasurementGroupNotificationService(
    private val context: Context
) {

    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    fun showNotification(measurementGroup: MeasurementGroupDomainModel) {
        val args = Bundle().apply {
            putString("scheduledMeasurementGroupId", measurementGroup.id)
        }

        val pendingIntent = NavDeepLinkBuilder(context)
            .setGraph(R.navigation.nav_measurement)
            .setDestination(R.id.listMeasurementFragment)
            .setArguments(args)
            .createPendingIntent()

        val notification = NotificationCompat.Builder(context, MeasurementGroupAlarmReceiver.CHANNEL_ID)
            .setSmallIcon(cz.vvoleman.phr.common_datasource.R.drawable.ic_health)
            .setContentTitle(context.resources.getString(R.string.notification_measurement_group_title))
            .setContentText(
                context.resources.getString(
                    R.string.notification_measurement_group_text,
                    measurementGroup.name
                )
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setColor(context.getColor(cz.vvoleman.phr.base.R.color.color_primary))
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .build()

        notificationManager.notify(measurementGroup.id.hashCode(), notification)
    }

}
