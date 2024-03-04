package cz.vvoleman.phr.data.fixture

import cz.vvoleman.phr.featureMeasurement.data.datasource.room.unit.UnitDataSourceModel
import cz.vvoleman.phr.featureMeasurement.data.datasource.room.unit.UnitGroupDao
import cz.vvoleman.phr.featureMeasurement.data.datasource.room.unit.UnitGroupDataSourceModel

class UnitGroupFixture(
    private val unitGroupDao: UnitGroupDao,
) {

    suspend fun setup(): List<UnitGroupDataSourceModel> {
        val data = getData()

        data.forEach {
            unitGroupDao.insert(it)
        }

        return data
    }

    private fun getData(): List<UnitGroupDataSourceModel> {
        val units = getUnits()

        return listOf(
            UnitGroupDataSourceModel(
                id = 1,
                name = "Hmotnost",
                units = listOf(
                    units["kg"]!!,
                    units["g"]!!,
                    units["lb"]!!,
                ),
                defaultUnit = units["kg"]!!
            ),
            UnitGroupDataSourceModel(
                id = 2,
                name = "Délka",
                units = listOf(
                    units["m"]!!,
                    units["cm"]!!,
                    units["ft"]!!
                ),
                defaultUnit = units["cm"]!!
            ),
            UnitGroupDataSourceModel(
                id = 3,
                name = "Teplota",
                units = listOf(
                    units["c"]!!,
                    units["f"]!!,
                    units["k"]!!
                ),
                defaultUnit = units["c"]!!
            ),
            UnitGroupDataSourceModel(
                id = 3,
                name = "Jiné",
                units = listOf(
                    units["custom"]!!
                ),
                defaultUnit = units["custom"]!!
            ),
        )
    }

    private fun getUnits(): Map<String, UnitDataSourceModel> {
        return mapOf(
            Pair(
                "kg", UnitDataSourceModel(
                    code = "kg",
                    name = "Kilogram",
                    multiplier = 1,
                    addition = 0
                )
            ),
            Pair(
                "g", UnitDataSourceModel(
                    code = "g",
                    name = "Gram",
                    multiplier = 0.001,
                    addition = 0
                )
            ),
            Pair(
                "lb", UnitDataSourceModel(
                    code = "lb",
                    name = "Libra",
                    multiplier = 0.45359237,
                    addition = 0
                )
            ),
            Pair(
                "m", UnitDataSourceModel(
                    code = "m",
                    name = "Metr",
                    multiplier = 1,
                    addition = 0
                )
            ),
            Pair(
                "ft", UnitDataSourceModel(
                    code = "ft",
                    name = "Stopa",
                    multiplier = 0.3048,
                    addition = 0
                )
            ),
            Pair(
                "cm", UnitDataSourceModel(
                    code = "cm",
                    name = "Centimetr",
                    multiplier = 0.01,
                    addition = 0
                )
            ),
            Pair(
                "c", UnitDataSourceModel(
                    code = "c",
                    name = "Celsia",
                    multiplier = 1,
                    addition = 0
                )
            ),
            Pair(
                "f", UnitDataSourceModel(
                    code = "f",
                    name = "Fahrenheit",
                    multiplier = 1.8,
                    addition = 32
                )
            ),
            Pair(
                "k", UnitDataSourceModel(
                    code = "k",
                    name = "Kelvin",
                    multiplier = 1,
                    addition = 273.15
                )
            ),
            Pair(
                "custom", UnitDataSourceModel(
                    code = "",
                    name = "Vlastní",
                    multiplier = 1,
                    addition = 0
                )
            ),
        )
    }

}
