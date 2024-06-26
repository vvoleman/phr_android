package cz.vvoleman.phr.featureMeasurement.data.mapper.core

import cz.vvoleman.phr.featureMeasurement.data.model.core.field.unit.UnitGroupDataModel
import cz.vvoleman.phr.featureMeasurement.domain.model.core.field.unit.UnitGroupDomainModel

class UnitGroupDataModelToDomainMapper(
    private val unitMapper: UnitDataModelToDomainMapper
) {

    fun toDomain(model: UnitGroupDataModel): UnitGroupDomainModel {
        return UnitGroupDomainModel(
            id = model.id,
            name = model.name,
            units = unitMapper.toDomain(model.units),
            defaultUnit = unitMapper.toDomain(model.defaultUnit)
        )
    }

    fun toDomain(models: List<UnitGroupDataModel>): List<UnitGroupDomainModel> {
        return models.map { toDomain(it) }
    }

    fun toData(model: UnitGroupDomainModel): UnitGroupDataModel {
        return UnitGroupDataModel(
            id = model.id,
            name = model.name,
            units = unitMapper.toData(model.units),
            defaultUnit = unitMapper.toData(model.defaultUnit)
        )
    }

    fun toData(models: List<UnitGroupDomainModel>): List<UnitGroupDataModel> {
        return models.map { toData(it) }
    }
}
