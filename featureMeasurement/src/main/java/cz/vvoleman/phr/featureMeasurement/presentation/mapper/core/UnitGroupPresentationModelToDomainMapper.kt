package cz.vvoleman.phr.featureMeasurement.presentation.mapper.core

import cz.vvoleman.phr.featureMeasurement.domain.model.core.field.unit.UnitGroupDomainModel
import cz.vvoleman.phr.featureMeasurement.presentation.model.core.field.unit.UnitGroupPresentationModel

class UnitGroupPresentationModelToDomainMapper(
    private val unitMapper: UnitPresentationModelToDomainMapper
) {

    fun toDomain(model: UnitGroupPresentationModel): UnitGroupDomainModel {
        return UnitGroupDomainModel(
            id = model.id,
            name = model.name,
            units = model.units.map { unitMapper.toDomain(it) },
            defaultUnit = unitMapper.toDomain(model.defaultUnit),
        )
    }

    fun toPresentation(model: UnitGroupDomainModel): UnitGroupPresentationModel {
        return UnitGroupPresentationModel(
            id = model.id,
            name = model.name,
            units = model.units.map { unitMapper.toPresentation(it) },
            defaultUnit = unitMapper.toPresentation(model.defaultUnit),
        )
    }
}
