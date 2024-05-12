package cz.vvoleman.phr.featureEvent.ui.usecase

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.provider.CalendarContract
import android.util.Log
import androidx.core.content.ContextCompat
import cz.vvoleman.phr.featureEvent.ui.model.core.EventUiModel
import java.time.Instant
import java.time.ZoneId
import java.time.ZoneOffset

class ExportEventsToCalendarUseCase(
    private val context: Context,
) {

    @Suppress("MagicNumber")
    suspend fun execute(events: List<EventUiModel>): Boolean {
        if (checkPermissions()) {
            Log.d("ExportEventsToCalendarUseCase", "No permissions")
            return false
        }

        var result = true

        for (event in events) {
            val endAt = event.endAt ?: event.startAt.plusHours(1)
            val zoneOffset = ZoneId.of("Europe/Prague").rules.getOffset(Instant.now())
            val values = ContentValues().apply {
                put(CalendarContract.Events.DTSTART, event.startAt.toEpochSecond(zoneOffset) * 1000L)
                put(CalendarContract.Events.DTEND, endAt.toEpochSecond(zoneOffset) * 1000L)
//                put(CalendarContract.Events.DURATION, "P1H")
                put(CalendarContract.Events.TITLE, event.name)
                put(CalendarContract.Events.DESCRIPTION, event.description)
                put(CalendarContract.Events.EVENT_LOCATION, event.specificMedicalWorker?.medicalWorker?.name)
                put(CalendarContract.Events.CALENDAR_ID, 1)
                put(CalendarContract.Events.EVENT_TIMEZONE, "Europe/Prague")
            }

            Log.d("ExportEventsToCalendarUseCase", "Inserting event: $values")

            val uri = context.contentResolver.insert(CalendarContract.Events.CONTENT_URI, values)
            val eventId = uri?.lastPathSegment?.toLongOrNull() ?: continue

            Log.d("ExportEventsToCalendarUseCase", "Event inserted with id: $eventId")

            for (reminder in event.reminders) {
                val minutes = event.startAt.minusSeconds(reminder.offset).toEpochSecond(ZoneOffset.UTC) / 60
                val reminderValues = ContentValues().apply {
                    put(CalendarContract.Reminders.MINUTES, minutes)
                    put(CalendarContract.Reminders.EVENT_ID, eventId)
                    put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT)
                }

                val reminderUri = context.contentResolver.insert(CalendarContract.Reminders.CONTENT_URI, reminderValues)
                if (reminderUri == null) {
                    result = false
                }
            }
        }

        return result
    }

    private fun checkPermissions(): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.WRITE_CALENDAR
        ) != PackageManager.PERMISSION_GRANTED
    }
}
