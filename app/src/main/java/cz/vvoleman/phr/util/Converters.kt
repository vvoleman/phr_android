package cz.vvoleman.phr.util

import android.util.Log
import androidx.room.TypeConverter
import cz.vvoleman.phr.data.core.Color
import java.time.LocalDate
import java.util.*

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): LocalDate? {
        return value?.let { LocalDate.ofEpochDay(it) }
    }

    @TypeConverter
    fun localDateToTimestamp(date: LocalDate?): Long? {
        Log.d("Converters", "localDateToTimestamp: $date")
        return date?.toEpochDay()
    }

    @TypeConverter
    fun colorToString(value: Color): String {
        return value.name
    }

    @TypeConverter
    fun stringToColor(value: String): Color {
        return Color.fromString(value)
    }
}