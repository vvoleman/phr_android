package cz.vvoleman.phr.featureMeasurement.presentation.mapper.core

import cz.vvoleman.phr.common.presentation.mapper.PatientPresentationModelToDomainMapper
import cz.vvoleman.phr.common.presentation.mapper.problemCategory.ProblemCategoryPresentationModelToDomainMapper
import cz.vvoleman.phr.featureMeasurement.domain.model.core.MeasurementGroupDomainModel
import cz.vvoleman.phr.featureMeasurement.presentation.model.core.MeasurementGroupPresentationModel

class MeasurementGroupPresentationModelToDomainMapper(
    private val scheduleItemMapper: MeasurementGroupScheduleItemPresentationModelToDomainMapper,
    private val entryMapper: MeasurementGroupEntryPresentationModelToDomainMapper,
    private val fieldMapper: MeasurementGroupFieldPresentationToDomainMapper,
    private val patientMapper: PatientPresentationModelToDomainMapper,
    private val problemCategoryMapper: ProblemCategoryPresentationModelToDomainMapper,
) {
    fun toDomain(model: MeasurementGroupPresentationModel): MeasurementGroupDomainModel {
        return MeasurementGroupDomainModel(
            id = model.id,
            name = model.name,
            scheduleItems = model.scheduleItems.map { scheduleItemMapper.toDomain(it) },
            entries = model.entries.map { entryMapper.toDomain(it) },
            fields = fieldMapper.toDomain(model.fields),
            patient = patientMapper.toDomain(model.patient),
            problemCategory = model.problemCategory?.let { problemCategoryMapper.toDomain(it) },
        )
    }

    fun toPresentation(model: MeasurementGroupDomainModel): MeasurementGroupPresentationModel {
        return MeasurementGroupPresentationModel(
            id = model.id,
            name = model.name,
            scheduleItems = model.scheduleItems.map { scheduleItemMapper.toPresentation(it) },
            entries = model.entries.map { entryMapper.toPresentation(it) },
            fields = fieldMapper.toPresentation(model.fields),
            patient = patientMapper.toPresentation(model.patient),
            problemCategory = model.problemCategory?.let { problemCategoryMapper.toPresentation(it) },
        )
    }
}
