package cz.vvoleman.phr.featureEvent.data.mapper.core

import cz.vvoleman.phr.featureEvent.domain.model.core.ReminderOffset

class LongToReminderOffsetMapper {

    fun toReminderOffset(value: Long): ReminderOffset {
        return options.firstOrNull { it.offset == value } ?: ReminderOffset.Custom(value)
    }

    fun toReminderOffset(value: List<Long>): List<ReminderOffset> {
        return value.map { toReminderOffset(it) }
    }

    fun toLong(value: ReminderOffset): Long {
        return value.offset
    }

    fun toLong(value: List<ReminderOffset>): List<Long> {
        return value.map { toLong(it) }
    }

    companion object {
        private val options = listOf(
            ReminderOffset.OneWeek,
            ReminderOffset.TwoDays,
            ReminderOffset.OneDay,
            ReminderOffset.TwoHours,
            ReminderOffset.OneHour,
            ReminderOffset.ThirtyMinutes,
            ReminderOffset.FifteenMinutes,
            ReminderOffset.TenMinutes,
            ReminderOffset.FiveMinutes,
            ReminderOffset.OneMinute,
            ReminderOffset.AtStart
        )
    }

}
