package cz.vvoleman.phr.featureMeasurement.data.mapper.core

import cz.vvoleman.phr.featureMeasurement.data.model.core.field.unit.UnitDataModel
import cz.vvoleman.phr.featureMeasurement.domain.model.core.field.unit.UnitDomainModel

class UnitDataModelToDomainMapper {

    fun toDomain(model: UnitDataModel): UnitDomainModel {
        return UnitDomainModel(
            code = model.code,
            name = model.name,
            multiplier = model.multiplier,
            addition = model.addition,
        )
    }

    fun toDomain(models: List<UnitDataModel>): List<UnitDomainModel> {
        return models.map { toDomain(it) }
    }

    fun toData(model: UnitDomainModel): UnitDataModel {
        return UnitDataModel(
            code = model.code,
            name = model.name,
            multiplier = model.multiplier,
            addition = model.addition,
        )
    }

    fun toData(models: List<UnitDomainModel>): List<UnitDataModel> {
        return models.map { toData(it) }
    }

}
