package cz.vvoleman.phr.featureMeasurement.presentation.mapper.core

import cz.vvoleman.phr.featureMeasurement.domain.model.core.MeasurementGroupFieldDomain
import cz.vvoleman.phr.featureMeasurement.domain.model.core.field.NumericFieldDomainModel
import cz.vvoleman.phr.featureMeasurement.presentation.model.core.MeasurementGroupFieldPresentation
import cz.vvoleman.phr.featureMeasurement.presentation.model.core.field.NumericFieldPresentationModel

class MeasurementGroupFieldPresentationToDomainMapper(
    private val numericMapper: NumericFieldPresentationModelToDomainMapper,
) {

    fun toDomain(model: MeasurementGroupFieldPresentation): MeasurementGroupFieldDomain {
        return when (model) {
            is NumericFieldPresentationModel -> numericMapper.toDomain(model)
            else -> throw IllegalArgumentException("Unknown field type")
        }
    }

    fun toDomain(list: List<MeasurementGroupFieldPresentation>): List<MeasurementGroupFieldDomain> {
        return list.map { toDomain(it) }
    }

    fun toPresentation(model: MeasurementGroupFieldDomain): MeasurementGroupFieldPresentation {
        return when (model) {
            is NumericFieldDomainModel -> numericMapper.toPresentation(model)
            else -> throw IllegalArgumentException("Unknown field type")
        }
    }

    fun toPresentation(list: List<MeasurementGroupFieldDomain>): List<MeasurementGroupFieldPresentation> {
        return list.map { toPresentation(it) }
    }

}
