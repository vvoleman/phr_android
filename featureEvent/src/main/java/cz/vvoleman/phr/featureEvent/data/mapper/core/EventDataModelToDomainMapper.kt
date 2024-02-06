package cz.vvoleman.phr.featureEvent.data.mapper.core

import cz.vvoleman.phr.featureEvent.data.model.core.EventDataModel
import cz.vvoleman.phr.featureEvent.domain.mapper.core.LongToReminderOffsetMapper
import cz.vvoleman.phr.featureEvent.domain.model.core.EventDomainModel

class EventDataModelToDomainMapper(
    private val reminderOffset: LongToReminderOffsetMapper
) {

    fun toDomain(model: EventDataModel): EventDomainModel {
        return EventDomainModel(
            id = model.id,
            name = model.name,
            startAt = model.startAt,
            endAt = model.endAt,
            patient = model.patient,
            specificMedicalWorker = model.specificMedicalWorker,
            description = model.description,
            reminders = reminderOffset.toReminderOffset(model.reminders)
        )
    }

    fun toDomain(model: List<EventDataModel>): List<EventDomainModel> {
        return model.map { toDomain(it) }
    }

    fun toData(model: EventDomainModel): EventDataModel {
        return EventDataModel(
            id = model.id,
            name = model.name,
            startAt = model.startAt,
            endAt = model.endAt,
            patient = model.patient,
            specificMedicalWorker = model.specificMedicalWorker,
            description = model.description,
            reminders = reminderOffset.toLong(model.reminders)
        )
    }

    fun toData(model: List<EventDomainModel>): List<EventDataModel> {
        return model.map { toData(it) }
    }

}
