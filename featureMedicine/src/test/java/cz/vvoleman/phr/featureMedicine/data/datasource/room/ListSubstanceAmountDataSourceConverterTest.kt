package cz.vvoleman.phr.featureMedicine.data.datasource.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import cz.vvoleman.phr.featureMedicine.data.datasource.room.medicine.SubstanceAmountDataSourceModel
import cz.vvoleman.phr.featureMedicine.data.datasource.room.schedule.ListSubstanceAmountDataSourceModel
import org.junit.Assert.assertEquals
import org.junit.jupiter.api.Test

class ListSubstanceAmountDataSourceConverterTest {

    @Test
    fun testFromListSubstanceAmountDataSourceModel() {
        val gson = Gson()
        val converter = ListSubstanceAmountDataSourceConverter()

        val substance1 = SubstanceAmountDataSourceModel("1", "100MG")
        val substance2 = SubstanceAmountDataSourceModel("2", "200MG")
        val listModel = ListSubstanceAmountDataSourceModel(listOf(substance1, substance2))

        val jsonString = converter.fromListSubstanceAmountDataSourceModel(listModel)

        val expectedJson = gson.toJson(listModel)

        assertEquals(expectedJson, jsonString)
    }

    @Test
    fun testToListSubstanceAmountDataSourceModel() {
        val gson = Gson()
        val converter = ListSubstanceAmountDataSourceConverter()

        val expectedSubstance1 = SubstanceAmountDataSourceModel("1", "100MG")
        val expectedSubstance2 = SubstanceAmountDataSourceModel("2", "200MG")
        val expectedListModel = ListSubstanceAmountDataSourceModel(listOf(expectedSubstance1, expectedSubstance2))

        val json = gson.toJson(expectedListModel)

        val resultModel = converter.toListSubstanceAmountDataSourceModel(json)

        assertEquals(expectedListModel, resultModel)
    }
}