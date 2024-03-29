package cz.vvoleman.phr.common.data.alarm

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import java.time.ZoneId

class AndroidAlarmScheduler(
    private val context: Context
) : AlarmScheduler {

    private val alarmManager = context.getSystemService(AlarmManager::class.java)

    @SuppressLint("MissingPermission")
    @Suppress("MagicNumber")
    override fun schedule(item: AlarmItem): Boolean {
        if (!checkPermissions()) {
            return false
        }

        val intent = getIntent(item)
        val seconds = item.time.atZone(ZoneId.systemDefault()).toEpochSecond()
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            seconds * 1000,
            PendingIntent.getBroadcast(
                context,
                item.id.hashCode(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )

        return true
    }

    override fun cancel(item: AlarmItem): Boolean {
        alarmManager.cancel(
            PendingIntent.getBroadcast(
                context,
                item.id.hashCode(),
                getIntent(item),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )

        return true
    }

    private fun getIntent(item: AlarmItem): Intent {
        return Intent(context, item.receiver).apply {
            putExtra(AlarmItem.CONTENT_KEY, item.content)
        }
    }

    private fun checkPermissions(): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.SET_ALARM
        ) != PackageManager.PERMISSION_GRANTED
    }
}
