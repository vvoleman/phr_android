package cz.vvoleman.phr.featureMedicine.data.datasource.room

import android.util.Log
import androidx.room.TypeConverter
import com.google.gson.Gson
import cz.vvoleman.phr.featureMedicine.data.datasource.room.medicine.SubstanceAmountDataSourceModel
import cz.vvoleman.phr.featureMedicine.data.datasource.room.schedule.ListSubstanceAmountDataSourceModel

class ListSubstanceAmountDataSourceConverter {

    @TypeConverter
    fun fromListSubstanceAmountDataSourceModel(model: ListSubstanceAmountDataSourceModel): String {
        return Gson().toJson(model)
    }

    @TypeConverter
    fun toListSubstanceAmountDataSourceModel(json: String): ListSubstanceAmountDataSourceModel {
        Log.d("ListSubstanceAmountDataSourceConverter", "toListSubstanceAmountDataSourceModel: $json")
        return Gson().fromJson(json, ListSubstanceAmountDataSourceModel::class.java)
    }

}