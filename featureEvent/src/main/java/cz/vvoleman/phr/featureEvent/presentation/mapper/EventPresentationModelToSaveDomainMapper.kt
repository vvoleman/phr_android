package cz.vvoleman.phr.featureEvent.presentation.mapper

import cz.vvoleman.phr.featureEvent.domain.model.addEdit.SaveEventDomainModel
import cz.vvoleman.phr.featureEvent.presentation.model.core.EventPresentationModel

class EventPresentationModelToSaveDomainMapper {

    fun toSave(model: EventPresentationModel): SaveEventDomainModel {
        return SaveEventDomainModel(
            id = model.id,
            name = model.name,
            description = model.description,
            startAt = model.startAt,
            endAt = model.endAt,
            patientId = model.patient.id,
            specificMedicalWorkerId = model.specificMedicalWorker?.id,
            reminders = model.reminders
        )
    }
}
