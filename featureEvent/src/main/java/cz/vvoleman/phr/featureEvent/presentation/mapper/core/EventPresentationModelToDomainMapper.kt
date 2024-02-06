package cz.vvoleman.phr.featureEvent.presentation.mapper.core

import cz.vvoleman.phr.common.presentation.mapper.PatientPresentationModelToDomainMapper
import cz.vvoleman.phr.common.presentation.mapper.healthcare.SpecificMedicalWorkerPresentationModelToDomainMapper
import cz.vvoleman.phr.featureEvent.domain.model.core.EventDomainModel
import cz.vvoleman.phr.featureEvent.presentation.model.core.EventPresentationModel

class EventPresentationModelToDomainMapper(
    private val patientMapper: PatientPresentationModelToDomainMapper,
    private val workerMapper: SpecificMedicalWorkerPresentationModelToDomainMapper,
) {

    fun toDomain(model: EventPresentationModel): EventDomainModel {
        return EventDomainModel(
            id = model.id,
            name = model.name,
            startAt = model.startAt,
            endAt = model.endAt,
            patient = patientMapper.toDomain(model.patient),
            specificMedicalWorker = workerMapper.toDomain(model.specificMedicalWorker),
            description = model.description,
            reminders = model.reminders
        )
    }

    fun toDomain(model: List<EventPresentationModel>): List<EventDomainModel> {
        return model.map { toDomain(it) }
    }

    fun toPresentation(model: EventDomainModel): EventPresentationModel {
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

    fun toPresentation(model: List<EventDomainModel>): List<EventPresentationModel> {
        return model.map { toPresentation(it) }
    }

}
