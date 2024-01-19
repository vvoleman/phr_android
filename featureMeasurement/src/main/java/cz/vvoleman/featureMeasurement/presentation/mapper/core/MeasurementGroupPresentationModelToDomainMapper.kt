package cz.vvoleman.featureMeasurement.presentation.mapper.core

import cz.vvoleman.featureMeasurement.domain.model.MeasurementGroupDomainModel
import cz.vvoleman.featureMeasurement.domain.model.MeasurementGroupField
import cz.vvoleman.featureMeasurement.domain.model.field.NumericFieldDomainModel
import cz.vvoleman.featureMeasurement.presentation.model.core.MeasurementGroupPresentationModel
import cz.vvoleman.featureMeasurement.presentation.model.core.field.NumericFieldPresentationModel
import cz.vvoleman.phr.common.presentation.mapper.PatientPresentationModelToDomainMapper

class MeasurementGroupPresentationModelToDomainMapper(
    private val scheduleItemMapper: MeasurementGroupScheduleItemPresentationModelToDomainMapper,
    private val entryMapper: MeasurementGroupEntryPresentationModelToDomainMapper,
    private val numericFieldMapper: NumericFieldPresentationModelToDomainMapper,
    private val patientMapper: PatientPresentationModelToDomainMapper,
) {

    fun toDomain(model: MeasurementGroupPresentationModel): MeasurementGroupDomainModel {
        return MeasurementGroupDomainModel(
            id = model.id,
            name = model.name,
            scheduleItems = model.scheduleItems.map { scheduleItemMapper.toDomain(it) },
            entries = model.entries.map { entryMapper.toDomain(it) },
            fields = mapFields(model.fields),
            patient = patientMapper.toDomain(model.patient),
        )
    }

    fun toPresentation(model: MeasurementGroupDomainModel): MeasurementGroupPresentationModel {
        return MeasurementGroupPresentationModel(
            id = model.id,
            name = model.name,
            scheduleItems = model.scheduleItems.map { scheduleItemMapper.toPresentation(it) },
            entries = model.entries.map { entryMapper.toPresentation(it) },
            fields = mapFields(model.fields),
            patient = patientMapper.toPresentation(model.patient),
        )
    }

    private fun mapFields(list: List<MeasurementGroupField>): List<MeasurementGroupField> {
        return list.map {
            when (it) {
                is NumericFieldPresentationModel -> numericFieldMapper.toDomain(it)
                is NumericFieldDomainModel -> numericFieldMapper.toPresentation(it)
                else -> {
                    throw IllegalArgumentException("Unknown type of MeasurementGroupField")
                }
            }
        }
    }

}
