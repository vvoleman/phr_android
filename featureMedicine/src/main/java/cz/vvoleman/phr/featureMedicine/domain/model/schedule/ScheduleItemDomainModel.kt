package cz.vvoleman.phr.featureMedicine.domain.model.schedule

import android.os.Parcelable
import cz.vvoleman.phr.featureMedicine.domain.facade.TranslateDateTimeFacade
import kotlinx.parcelize.Parcelize
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.LocalTime

@Parcelize
data class ScheduleItemDomainModel(
    val id: String? = null,
    val dayOfWeek: DayOfWeek,
    val time: LocalTime,
    val scheduledAt: LocalDateTime,
    val endingAt: LocalDateTime? = null,
    val quantity: Number,
    val unit: String,
    val description: String? = null,
    private var _translated: LocalDateTime? = null
): Parcelable {
    fun getTranslatedDateTime(currentDateTime: LocalDateTime): LocalDateTime {
        if (_translated != null) {
            return _translated!!
        }
        _translated = TranslateDateTimeFacade.translateScheduleItem(this, currentDateTime)
        return _translated!!
    }
}
