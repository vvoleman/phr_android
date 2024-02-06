package cz.vvoleman.phr

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import cz.vvoleman.phr.featureEvent.data.alarm.receiver.event.EventAlarmReceiver
import cz.vvoleman.phr.featureMeasurement.data.alarm.receiver.measurementGroup.MeasurementGroupAlarmReceiver
import cz.vvoleman.phr.featureMedicine.data.alarm.MedicineAlarmReceiver
import dagger.hilt.android.HiltAndroidApp
import org.greenrobot.eventbus.EventBus

@HiltAndroidApp
class PHRApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        createNotificationChannel()
        EventBus.builder().build()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channels = listOf(
                Channel(
                    MedicineAlarmReceiver.CHANNEL_ID,
                    "Léky",
                    NotificationManager.IMPORTANCE_DEFAULT,
                    "Upozorňuje na nadcházející užití léků"
                ),
                Channel(
                    MeasurementGroupAlarmReceiver.CHANNEL_ID,
                    "Měření",
                    NotificationManager.IMPORTANCE_DEFAULT,
                    "Upozorňuje na nadcházející měření hodnot"
                ),
                Channel(
                    EventAlarmReceiver.CHANNEL_ID,
                    "Plány návštěv",
                    NotificationManager.IMPORTANCE_DEFAULT,
                    "Upozorňuje na nadcházející návštěvy"
                )
            )

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            channels.forEach {
                val channel = NotificationChannel(
                    it.channelId,
                    it.name,
                    it.importance
                )
                channel.description = it.description

                notificationManager.createNotificationChannel(channel)
            }
        }
    }

    private data class Channel(
        val channelId: String,
        val name: String,
        val importance: Int,
        val description: String,
    )
}
