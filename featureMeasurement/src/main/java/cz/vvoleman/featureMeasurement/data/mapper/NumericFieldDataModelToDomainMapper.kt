package cz.vvoleman.featureMeasurement.data.mapper

import cz.vvoleman.featureMeasurement.data.model.field.NumericFieldDataModel
import cz.vvoleman.featureMeasurement.domain.model.core.field.NumericFieldDomainModel

class NumericFieldDataModelToDomainMapper(
    private val unitGroupMapper: UnitGroupDataModelToDomainMapper
) {

    fun toDomain(model: NumericFieldDataModel): NumericFieldDomainModel {
        return NumericFieldDomainModel(
            id = model.id,
            name = model.name,
            unitGroup = unitGroupMapper.toDomain(model.unitGroup),
            minimalValue = model.minimalValue,
            maximalValue = model.maximalValue
        )
    }

    fun toDomain(models: List<NumericFieldDataModel>): List<NumericFieldDomainModel> {
        return models.map { toDomain(it) }
    }

    fun toData(model: NumericFieldDomainModel): NumericFieldDataModel {
        return NumericFieldDataModel(
            id = model.id,
            name = model.name,
            unitGroup = unitGroupMapper.toData(model.unitGroup),
            minimalValue = model.minimalValue,
            maximalValue = model.maximalValue
        )
    }

    fun toData(models: List<NumericFieldDomainModel>): List<NumericFieldDataModel> {
        return models.map { toData(it) }
    }

}
