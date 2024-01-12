package cz.vvoleman.featureMeasurement.data.datasource.room.mapper

import cz.vvoleman.featureMeasurement.data.datasource.room.field.NumericFieldDataSourceModel
import cz.vvoleman.featureMeasurement.data.datasource.room.unit.UnitGroupDao
import cz.vvoleman.featureMeasurement.data.model.field.NumericFieldDataModel
import kotlinx.coroutines.flow.firstOrNull

class NumericFieldDataSourceModelToDataMapper(
    private val unitGroupMapper: UnitGroupDataSourceModelToDataMapper,
    private val unitGroupDao: UnitGroupDao
) {

    suspend fun toData(model: NumericFieldDataSourceModel): NumericFieldDataModel {
        val unitGroup = unitGroupDao.getById(model.unitGroupId.toString()).firstOrNull()
            ?: throw IllegalArgumentException("Unit group with id ${model.unitGroupId} not found")

        return NumericFieldDataModel(
            id = model.id.toString(),
            name = model.name,
            unitGroup = unitGroupMapper.toData(unitGroup),
        )
    }

    suspend fun toData(models: List<NumericFieldDataSourceModel>): List<NumericFieldDataModel> {
        return models.map { toData(it) }
    }

    fun toDataSource(model: NumericFieldDataModel, measurementGroupId: String): NumericFieldDataSourceModel {
        return NumericFieldDataSourceModel(
            id = model.id.toIntOrNull(),
            name = model.name,
            unitGroupId = model.unitGroup.id.toInt(),
            measurementGroupId = measurementGroupId.toInt()
        )
    }

    fun toDataSource(
        models: List<NumericFieldDataModel>,
        measurementGroupId: String
    ): List<NumericFieldDataSourceModel> {
        return models.map { toDataSource(it, measurementGroupId) }
    }

}
