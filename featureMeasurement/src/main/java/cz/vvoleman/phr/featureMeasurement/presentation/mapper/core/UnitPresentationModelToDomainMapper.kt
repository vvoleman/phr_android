package cz.vvoleman.phr.featureMeasurement.presentation.mapper.core

import cz.vvoleman.phr.featureMeasurement.domain.model.core.field.unit.UnitDomainModel
import cz.vvoleman.phr.featureMeasurement.presentation.model.core.field.unit.UnitPresentationModel

class UnitPresentationModelToDomainMapper {

    fun toDomain(model: UnitPresentationModel): UnitDomainModel {
        return UnitDomainModel(
            code = model.code,
            name = model.name,
            multiplier = model.multiplier,
            addition = model.addition,
        )
    }

    fun toPresentation(model: UnitDomainModel): UnitPresentationModel {
        return UnitPresentationModel(
            code = model.code,
            name = model.name,
            multiplier = model.multiplier,
            addition = model.addition,
        )
    }
}
