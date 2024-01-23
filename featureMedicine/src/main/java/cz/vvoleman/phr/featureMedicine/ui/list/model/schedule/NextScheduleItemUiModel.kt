package cz.vvoleman.phr.featureMedicine.ui.list.model.schedule

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NextScheduleItemUiModel(
    val scheduleItems: List<ScheduleItemWithDetailsUiModel>,
    val id: String,
) : Parcelable
