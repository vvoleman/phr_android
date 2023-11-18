package cz.vvoleman.phr.common.data.alarm

import android.app.AlarmManager
import android.os.Parcelable
import java.time.LocalTime

data class AlarmItem(
    val id: String,
    val type: Int = AlarmManager.RTC_WAKEUP,
    val triggerAt: LocalTime,
    val repeatInterval: Long = 0,
    val content: Parcelable? = null,
    val receiver: Class<*>,
) {
    companion object {
        const val CONTENT_KEY = "content"
        const val REPEAT_DAY = 1000 * 60 * 60 * 24
        const val REPEAT_HOUR = 1000 * 60 * 60
        const val REPEAT_MINUTE = 1000 * 60
        const val REPEAT_SECOND = 1000
    }
}
