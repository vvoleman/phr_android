package cz.vvoleman.featureMeasurement.presentation.mapper.core

import cz.vvoleman.featureMeasurement.domain.model.core.field.unit.UnitDomainModel
import cz.vvoleman.featureMeasurement.presentation.model.core.field.unit.UnitPresentationModel

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
