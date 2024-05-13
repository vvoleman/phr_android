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
import cz.vvoleman.phr.featureMedicine.domain.model.schedule.MedicineScheduleDomainModel
import java.time.LocalDateTime

class MedicineNotificationService(
    private val context: Context
) {

    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    fun showNotification(medicine: MedicineScheduleDomainModel, timestamp: LocalDateTime) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("phr://medicine/add"))
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

        val notificationIntent = PendingIntent.getActivity(
            context,
            1,
            intent,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
        )

        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)

        val notification = NotificationCompat.Builder(context, MedicineAlarmReceiver.CHANNEL_ID)
            .setSmallIcon(R.drawable.baseline_healing_24)
            .setContentTitle(context.resources.getString(R.string.notification_medicine_title))
            .setContentText(
                context.resources.getString(
                    R.string.notification_medicine_text,
                    medicine.medicine.name,
                    timestamp.toLocalTime().toString()
                )
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(notificationIntent)
            .setSound(defaultSoundUri)
            .setColor(context.getColor(cz.vvoleman.phr.base.R.color.color_primary))
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .build()

        notificationManager.notify(medicine.id.hashCode(), notification)
    }
}
