package cz.vvoleman.phr.common.ui.fragment

import androidx.fragment.app.Fragment
import cz.vvoleman.phr.common.ui.adapter.grouped.GroupedItemsAdapter
import cz.vvoleman.phr.common.ui.model.GroupedItemsUiModel
import java.time.LocalDateTime

abstract class AbstractTimelineFragment<T: Any> : Fragment(), GroupedItemsAdapter.GroupedItemsAdapterInterface<T> {

    @Suppress("MagicNumber")
    protected fun isMultipleDays(schedules: List<GroupedItemsUiModel<T>>): Boolean {
        if (schedules.size < 2) {
            return false
        }

        val keys = schedules.map {
            it.value.toString().split("-").take(GROUP_STRING_PARTS).joinToString("-")
        }.toMutableList()

        val firstDate = keys.removeFirst()

        return !keys.all { it == firstDate }
    }

    @Suppress("MagicNumber")
    protected fun getDateFromValue(value: String): LocalDateTime {
        val date = value.split("-")

        if (date.size != TIMESTAMP_PARTS) {
            return LocalDateTime.now()
        }

        return LocalDateTime.of(date[0].toInt(), date[1].toInt(), date[2].toInt(), date[3].toInt(), 0)
    }

    companion object {
        const val TIMESTAMP_PARTS = 4
        const val GROUP_STRING_PARTS = 3
    }

}
