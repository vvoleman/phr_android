package cz.vvoleman.featureMeasurement.data.mapper

import cz.vvoleman.featureMeasurement.data.model.MeasurementGroupFieldData
import cz.vvoleman.featureMeasurement.data.model.field.NumericFieldDataModel
import cz.vvoleman.featureMeasurement.domain.model.core.MeasurementGroupFieldDomain
import cz.vvoleman.featureMeasurement.domain.model.core.field.NumericFieldDomainModel

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
