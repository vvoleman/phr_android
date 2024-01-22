package cz.vvoleman.featureMeasurement.data.datasource.room.mapper

import cz.vvoleman.featureMeasurement.data.datasource.room.unit.UnitDataSourceModel
import cz.vvoleman.featureMeasurement.data.model.core.field.unit.UnitDataModel

class UnitDataSourceModelToDataMapper {

    fun toData(model: UnitDataSourceModel): UnitDataModel {
        return UnitDataModel(
            code = model.code,
            name = model.name,
            multiplier = model.multiplier,
            addition = model.addition,
        )
    }

    fun toData(models: List<UnitDataSourceModel>): List<UnitDataModel> {
        return models.map { toData(it) }
    }

    fun toDataSource(model: UnitDataModel): UnitDataSourceModel {
        return UnitDataSourceModel(
            code = model.code,
            name = model.name,
            multiplier = model.multiplier,
            addition = model.addition,
        )
    }

    fun toDataSource(models: List<UnitDataModel>): List<UnitDataSourceModel> {
        return models.map { toDataSource(it) }
    }
}
