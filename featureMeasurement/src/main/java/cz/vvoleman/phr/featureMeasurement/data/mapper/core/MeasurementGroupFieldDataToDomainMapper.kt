package cz.vvoleman.phr.featureMeasurement.data.mapper.core

import cz.vvoleman.phr.featureMeasurement.data.model.core.MeasurementGroupFieldData
import cz.vvoleman.phr.featureMeasurement.data.model.core.field.NumericFieldDataModel
import cz.vvoleman.phr.featureMeasurement.domain.model.core.MeasurementGroupFieldDomain
import cz.vvoleman.phr.featureMeasurement.domain.model.core.field.NumericFieldDomainModel

class MeasurementGroupFieldDataToDomainMapper(
    private val numericMapper: NumericFieldDataModelToDomainMapper,
) {

    fun toDomain(model: MeasurementGroupFieldData): MeasurementGroupFieldDomain {
        return when (model) {
            is NumericFieldDataModel -> numericMapper.toDomain(model)
            else -> throw IllegalArgumentException("Unknown field type")
        }
    }

    fun toDomain(list: List<MeasurementGroupFieldData>): List<MeasurementGroupFieldDomain> {
        return list.map { toDomain(it) }
    }

    fun toData(model: MeasurementGroupFieldDomain): MeasurementGroupFieldData {
        return when (model) {
            is NumericFieldDomainModel -> numericMapper.toData(model)
            else -> throw IllegalArgumentException("Unknown field type")
        }
    }

    fun toData(list: List<MeasurementGroupFieldDomain>): List<MeasurementGroupFieldData> {
        return list.map { toData(it) }
    }
}
