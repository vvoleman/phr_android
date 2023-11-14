package cz.vvoleman.phr.featureMedicine.ui.list.model.schedule

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class NextScheduleItemUiModel (
    val scheduleItems: List<ScheduleItemWithDetailsUiModel>,
    val dateTime: LocalDateTime
) : Parcelable