package cz.vvoleman.featureMeasurement.data.datasource.room.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import cz.vvoleman.featureMeasurement.data.datasource.room.unit.UnitDataSourceModel

class UnitConverter {

    @TypeConverter
    fun fromListOfUnit(list: List<UnitDataSourceModel>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun toListOfUnit(json: String): List<UnitDataSourceModel> {
        return Gson().fromJson(json, Array<UnitDataSourceModel>::class.java).toList()
    }

    @TypeConverter
    fun fromUnit(unit: UnitDataSourceModel): String {
        return Gson().toJson(unit)
    }

    @TypeConverter
    fun toUnit(json: String): UnitDataSourceModel {
        return Gson().fromJson(json, UnitDataSourceModel::class.java)
    }
}
