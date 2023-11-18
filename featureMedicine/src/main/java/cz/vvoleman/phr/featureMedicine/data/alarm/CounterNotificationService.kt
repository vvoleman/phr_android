package cz.vvoleman.phr.featureMedicine.data.alarm

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import cz.vvoleman.phr.featureMedicine.R

class CounterNotificationService(
    private val context: Context
) {

    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    fun showNotification(counter: Int) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("phr://medicine/add"))
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

        val notificationIntent = PendingIntent.getActivity(
            context,
            1,
            intent,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
        )

        val incrementIntent = PendingIntent.getBroadcast(
            context,
            2,
            Intent(context, CounterNotificationReceiver::class.java),
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
        )

        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)

        val notification = NotificationCompat.Builder(context, MedicineAlarmReceiver.CHANNEL_ID)
            .setSmallIcon(R.drawable.baseline_healing_24)
            .setContentTitle("Upozornění na léky")
            .setContentText("Je čas vzít si léky ($counter)")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(notificationIntent)
            .addAction(R.drawable.baseline_healing_24, "Vzít léky", incrementIntent)
            .setSound(defaultSoundUri)
            .setColor(context.getColor(cz.vvoleman.phr.base.R.color.color_primary))
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .build()

        notificationManager.notify(counter, notification)
    }
}
