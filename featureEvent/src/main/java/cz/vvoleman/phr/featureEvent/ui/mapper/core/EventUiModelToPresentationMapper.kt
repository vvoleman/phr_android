package cz.vvoleman.phr.featureEvent.ui.mapper.core

import cz.vvoleman.phr.common.ui.mapper.healthcare.SpecificMedicalWorkerUiModelToPresentationMapper
import cz.vvoleman.phr.common.ui.mapper.patient.PatientUiModelToPresentationMapper
import cz.vvoleman.phr.featureEvent.presentation.model.core.EventPresentationModel
import cz.vvoleman.phr.featureEvent.ui.model.core.EventUiModel

class EventUiModelToPresentationMapper(
    private val patientMapper: PatientUiModelToPresentationMapper,
    private val workerMapper: SpecificMedicalWorkerUiModelToPresentationMapper
) {

    fun toPresentation(model: EventUiModel): EventPresentationModel {
        return EventPresentationModel(
            id = model.id,
            name = model.name,
            startAt = model.startAt,
            endAt = model.endAt,
            patient = patientMapper.toPresentation(model.patient),
            specificMedicalWorker = workerMapper.toPresentation(model.specificMedicalWorker),
            description = model.description,
            reminders = model.reminders
        )
    }

    fun toPresentation(model: List<EventUiModel>): List<EventPresentationModel> {
        return model.map { toPresentation(it) }
    }

    fun toUi(model: EventPresentationModel): EventUiModel {
        return EventUiModel(
            id = model.id,
            name = model.name,
            startAt = model.startAt,
            endAt = model.endAt,
            patient = patientMapper.toUi(model.patient),
            specificMedicalWorker = workerMapper.toUi(model.specificMedicalWorker),
            description = model.description,
            reminders = model.reminders
        )
    }

    fun toUi(model: List<EventPresentationModel>): List<EventUiModel> {
        return model.map { toUi(it) }
    }

}
