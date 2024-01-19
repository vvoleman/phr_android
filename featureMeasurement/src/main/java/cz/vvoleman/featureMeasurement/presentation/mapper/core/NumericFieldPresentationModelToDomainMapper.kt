package cz.vvoleman.featureMeasurement.presentation.mapper.core

import cz.vvoleman.featureMeasurement.domain.model.field.NumericFieldDomainModel
import cz.vvoleman.featureMeasurement.presentation.model.core.field.NumericFieldPresentationModel

class NumericFieldPresentationModelToDomainMapper(
    private val unitGroupMapper: UnitGroupPresentationModelToDomainMapper,
) {

    fun toDomain(model: NumericFieldPresentationModel): NumericFieldDomainModel {
        return NumericFieldDomainModel(
            id = model.id,
            name = model.name,
            unitGroup = unitGroupMapper.toDomain(model.unitGroup),
        )
    }

    fun toPresentation(model: NumericFieldDomainModel): NumericFieldPresentationModel {
        return NumericFieldPresentationModel(
            id = model.id,
            name = model.name,
            unitGroup = unitGroupMapper.toPresentation(model.unitGroup),
        )
    }

}
