package cz.vvoleman.phr.featureMeasurement.data.datasource.room.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ScheduleItemValueConverter {

    @TypeConverter
    fun fromListOfScheduleItemValue(list: Map<String, String>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun toListOfScheduleItemValue(json: String): Map<String, String> {
        val mapType = object : TypeToken<Map<String, String>>() {}.type
        return Gson().fromJson(json, mapType)
    }
}
