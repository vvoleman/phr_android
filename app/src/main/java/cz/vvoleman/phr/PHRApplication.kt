package cz.vvoleman.phr

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
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
            val channel = NotificationChannel(
                MedicineAlarmReceiver.CHANNEL_ID,
                "Medicine alarm",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = "Used for the increment counter notifications"

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
