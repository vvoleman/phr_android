package cz.vvoleman.featureMeasurement.data.mapper

import cz.vvoleman.featureMeasurement.data.model.MeasurementGroupDataModel
import cz.vvoleman.featureMeasurement.data.model.field.NumericFieldDataModel
import cz.vvoleman.featureMeasurement.domain.model.MeasurementGroupDomainModel
import cz.vvoleman.featureMeasurement.domain.model.MeasurementGroupField
import cz.vvoleman.featureMeasurement.domain.model.field.NumericFieldDomainModel

class MeasurementGroupDataModelToDomainMapper(
    private val numericFieldMapper: NumericFieldDataModelToDomainMapper,
    private val scheduleItemMapper: MeasurementGroupScheduleItemDataModelToDomainMapper,
    private val entryMapper: MeasurementGroupEntryDataModelToDomainMapper,
) {

    fun toDomain(model: MeasurementGroupDataModel): MeasurementGroupDomainModel {
        return MeasurementGroupDomainModel(
            id = model.id,
            name = model.name,
            patient = model.patient,
            scheduleItems = scheduleItemMapper.toDomain(model.scheduleItems),
            fields = mapFields(model.fields),
            entries = entryMapper.toDomain(model.entries),
        )
    }

    fun toDomain(models: List<MeasurementGroupDataModel>): List<MeasurementGroupDomainModel> {
        return models.map { toDomain(it) }
    }

    fun toData(model: MeasurementGroupDomainModel): MeasurementGroupDataModel {
        return MeasurementGroupDataModel(
            id = model.id,
            name = model.name,
            patient = model.patient,
            scheduleItems = scheduleItemMapper.toData(model.scheduleItems),
            fields = mapFields(model.fields),
            entries = entryMapper.toData(model.entries),
        )
    }

    fun toData(models: List<MeasurementGroupDomainModel>): List<MeasurementGroupDataModel> {
        return models.map { toData(it) }
    }

    private fun mapFields(list: List<MeasurementGroupField>): List<MeasurementGroupField> {
        return list.map {
            when (it) {
                is NumericFieldDataModel -> numericFieldMapper.toDomain(it)
                is NumericFieldDomainModel -> numericFieldMapper.toData(it)
                else -> {
                    throw IllegalArgumentException("Unknown type of MeasurementGroupField")
                }
            }
        }
    }

}
