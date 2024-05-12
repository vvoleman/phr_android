package cz.vvoleman.phr.featureMeasurement.data.datasource.room.mapper

import cz.vvoleman.phr.featureMeasurement.data.datasource.room.field.NumericFieldDataSourceModel
import cz.vvoleman.phr.featureMeasurement.data.datasource.room.unit.UnitGroupDao
import cz.vvoleman.phr.featureMeasurement.data.model.core.field.NumericFieldDataModel
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
            minimalValue = model.minimalValue,
            maximalValue = model.maximalValue
        )
    }

    suspend fun toData(models: List<NumericFieldDataSourceModel>): List<NumericFieldDataModel> {
        return models.map { toData(it) }
    }

    fun toDataSource(model: NumericFieldDataModel, measurementGroupId: String): NumericFieldDataSourceModel {
        return NumericFieldDataSourceModel(
            id = model.id.toLongOrNull(),
            name = model.name,
            unitGroupId = model.unitGroup.id.toInt(),
            measurementGroupId = measurementGroupId.toInt(),
            minimalValue = model.minimalValue,
            maximalValue = model.maximalValue
        )
    }

    fun toDataSource(
        models: List<NumericFieldDataModel>,
        measurementGroupId: String
    ): List<NumericFieldDataSourceModel> {
        return models.map { toDataSource(it, measurementGroupId) }
    }
}
