package cz.vvoleman.phr.util

import android.util.Log
import androidx.room.TypeConverter
import com.google.gson.Gson
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): LocalDate? {
        Log.d("Converters", "fromTimestamp: $value")
        return value?.let { LocalDate.ofEpochDay(it) }
    }

    @TypeConverter
    fun localDateToTimestamp(date: LocalDate?): Long? {
        Log.d("Converters", "localDateToTimestamp: $date")
        return date?.toEpochDay()
    }

    @TypeConverter
    fun fromLocalTime(value: LocalTime?): Long? {
        return value?.toNanoOfDay()
    }

    @TypeConverter
    fun toLocalTime(value: Long?): LocalTime? {
        return value?.let { LocalTime.ofNanoOfDay(it) }
    }

    @TypeConverter
    fun fromLocalDateTime(value: LocalDateTime?): Long? {
        return value?.toEpochSecond(java.time.ZoneOffset.UTC)
    }

    @TypeConverter
    fun toLocalDateTime(value: Long?): LocalDateTime? {
        return value?.let { LocalDateTime.ofEpochSecond(it, 0, java.time.ZoneOffset.UTC) }
    }

    @TypeConverter
    fun fromNumber(value: Number?): Double? {
        return value?.toDouble()
    }

    @TypeConverter
    fun toNumber(value: Double?): Number? {
        return value
    }

    @TypeConverter
    fun fromLongList(list: List<Long>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun toLongList(list: String): List<Long> {
        return Gson().fromJson(list, List::class.java) as List<Long>
    }
}
