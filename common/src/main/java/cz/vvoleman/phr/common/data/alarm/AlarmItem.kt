package cz.vvoleman.phr.common.data.alarm

import android.os.Parcelable
import java.time.LocalDateTime

data class AlarmItem(
    val id: String,
    val time: LocalDateTime,
    val content: Parcelable,
    val receiver: Class<*>,
) {
    companion object {
        const val CONTENT_KEY = "content"
    }
}