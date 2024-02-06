package cz.vvoleman.phr.featureEvent.domain.model.core

import cz.vvoleman.phr.common.utils.TimeConstants

sealed class ReminderOffset(val offset: Long) {
    object OneWeek : ReminderOffset(7 * TimeConstants.DAY_EPOCH)
    object TwoDays : ReminderOffset(2 * TimeConstants.DAY_EPOCH)
    object OneDay : ReminderOffset(TimeConstants.DAY_EPOCH)
    object TwoHours : ReminderOffset(2 * TimeConstants.HOUR_EPOCH)
    object OneHour : ReminderOffset(TimeConstants.HOUR_EPOCH)
    object ThirtyMinutes : ReminderOffset(30 * TimeConstants.MINUTE_EPOCH)
    object FifteenMinutes : ReminderOffset(15 * TimeConstants.MINUTE_EPOCH)
    object TenMinutes : ReminderOffset(10 * TimeConstants.MINUTE_EPOCH)
    object FiveMinutes : ReminderOffset(5 * TimeConstants.MINUTE_EPOCH)
    object OneMinute : ReminderOffset(TimeConstants.MINUTE_EPOCH)
    object AtStart : ReminderOffset(0)
    data class Custom(val customOffset: Long) : ReminderOffset(customOffset)
}
