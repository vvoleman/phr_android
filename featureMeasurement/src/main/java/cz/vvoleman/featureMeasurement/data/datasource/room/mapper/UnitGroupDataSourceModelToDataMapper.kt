package cz.vvoleman.featureMeasurement.data.datasource.room.mapper

import cz.vvoleman.featureMeasurement.data.datasource.room.unit.UnitGroupDataSourceModel
import cz.vvoleman.featureMeasurement.data.model.core.field.unit.UnitGroupDataModel

class UnitGroupDataSourceModelToDataMapper(
    private val unitMapper: UnitDataSourceModelToDataMapper
) {

    fun toData(model: UnitGroupDataSourceModel): UnitGroupDataModel {
        return UnitGroupDataModel(
            id = model.id.toString(),
            name = model.name,
            units = unitMapper.toData(model.units),
            defaultUnit = unitMapper.toData(model.defaultUnit)
        )
    }

    fun toDataSource(model: UnitGroupDataModel): UnitGroupDataSourceModel {
        return UnitGroupDataSourceModel(
            id = model.id.toIntOrNull(),
            name = model.name,
            units = unitMapper.toDataSource(model.units),
            defaultUnit = unitMapper.toDataSource(model.defaultUnit)
        )
    }

}
