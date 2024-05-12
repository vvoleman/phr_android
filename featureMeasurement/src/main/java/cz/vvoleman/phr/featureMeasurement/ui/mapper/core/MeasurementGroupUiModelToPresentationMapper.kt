package cz.vvoleman.phr.featureMeasurement.ui.mapper.core

import cz.vvoleman.phr.common.ui.mapper.patient.PatientUiModelToPresentationMapper
import cz.vvoleman.phr.common.ui.mapper.problemCategory.ProblemCategoryUiModelToPresentationMapper
import cz.vvoleman.phr.featureMeasurement.presentation.model.core.MeasurementGroupPresentationModel
import cz.vvoleman.phr.featureMeasurement.ui.model.core.MeasurementGroupUiModel

class MeasurementGroupUiModelToPresentationMapper(
    private val scheduleItemMapper: MeasurementGroupScheduleItemUiModelToPresentationMapper,
    private val entryMapper: MeasurementGroupEntryUiModelToPresentationMapper,
    private val fieldMapper: MeasurementGroupFieldUiModelToPresentationMapper,
    private val patientMapper: PatientUiModelToPresentationMapper,
    private val problemCategoryMapper: ProblemCategoryUiModelToPresentationMapper,
) {

    fun toPresentation(model: MeasurementGroupUiModel): MeasurementGroupPresentationModel {
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

    fun toUi(model: MeasurementGroupPresentationModel): MeasurementGroupUiModel {
        return MeasurementGroupUiModel(
            id = model.id,
            name = model.name,
            scheduleItems = model.scheduleItems.map { scheduleItemMapper.toUi(it) },
            entries = model.entries.map { entryMapper.toUi(it) },
            fields = fieldMapper.toUi(model.fields),
            patient = patientMapper.toUi(model.patient),
            problemCategory = model.problemCategory?.let { problemCategoryMapper.toUi(it) },
        )
    }
}
