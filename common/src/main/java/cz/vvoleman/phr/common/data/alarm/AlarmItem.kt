package cz.vvoleman.phr.common.data.alarm

import android.app.AlarmManager
import android.os.Parcelable
import java.time.LocalDateTime
import java.time.LocalTime

sealed class AlarmItem {
    abstract val id: String
    abstract val type: Int
    abstract val receiver: Class<*>
    abstract val content: Parcelable?

    companion object {
        const val CONTENT_KEY = "content"
    }

    data class Repeat(
        override val id: String,
        override val type: Int = AlarmManager.RTC_WAKEUP,
        val triggerAt: LocalTime,
        val repeatInterval: Long = 0,
        override val content: Parcelable? = null,
        override val receiver: Class<*>,
    ) : AlarmItem() {
        companion object {
            const val REPEAT_DAY = 1000 * 60 * 60 * 24
            const val REPEAT_HOUR = 1000 * 60 * 60
            const val REPEAT_MINUTE = 1000 * 60
            const val REPEAT_SECOND = 1000
        }
    }

    data class Single(
        override val id: String,
        override val type: Int = AlarmManager.RTC_WAKEUP,
        val triggerAt: LocalDateTime,
        override val content: Parcelable? = null,
        override val receiver: Class<*>,
    ) : AlarmItem()
}
