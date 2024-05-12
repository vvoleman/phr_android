package cz.vvoleman.phr.featureEvent.data.datasource.room.mapper

import cz.vvoleman.phr.featureEvent.data.datasource.room.EventDataSourceModel
import cz.vvoleman.phr.featureEvent.domain.model.addEdit.SaveEventDomainModel

class EventDataSourceModelToSaveDomainMapper {

    fun toDataSource(model: SaveEventDomainModel): EventDataSourceModel {
        return EventDataSourceModel(
            id = model.id?.toInt(),
            patientId = model.patientId.toInt(),
            name = model.name,
            description = model.description,
            startAt = model.startAt,
            endAt = model.endAt,
            specificMedicalWorkerId = model.specificMedicalWorkerId?.toInt(),
            reminders = model.reminders.map { it.offset },
        )
    }
}
