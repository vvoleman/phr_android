package cz.vvoleman.phr.featureEvent.data.datasource.room.mapper

import cz.vvoleman.phr.common.data.mapper.PatientDataSourceModelToDomainMapper
import cz.vvoleman.phr.common.data.mapper.healthcare.SpecificMedicalWorkerDataSourceToDomainMapper
import cz.vvoleman.phr.featureEvent.data.datasource.room.EventDataSourceModel
import cz.vvoleman.phr.featureEvent.data.datasource.room.EventWithDetailsDataSourceModel
import cz.vvoleman.phr.featureEvent.data.model.core.EventDataModel

class EventDataSourceModelToDataMapper(
    private val patientMapper: PatientDataSourceModelToDomainMapper,
    private val workerMapper: SpecificMedicalWorkerDataSourceToDomainMapper,
) {

    suspend fun toData(model: EventWithDetailsDataSourceModel): EventDataModel {
        return EventDataModel(
            id = model.event.id!!.toString(),
            name = model.event.name,
            startAt = model.event.startAt,
            endAt = model.event.endAt,
            patient = patientMapper.toDomain(model.patient),
            specificMedicalWorker = model.specificMedicalWorker
                ?.let { workerMapper.toDetails(it) }
                ?.let { workerMapper.toDomain(it) },
            description = model.event.description,
            reminders = model.event.reminders
        )
    }

    suspend fun toData(model: List<EventWithDetailsDataSourceModel>): List<EventDataModel> {
        return model.map { toData(it) }
    }

    fun toDataSourceModel(model: EventDataModel): EventDataSourceModel {
        return EventDataSourceModel(
            id = model.id.toInt(),
            name = model.name,
            startAt = model.startAt,
            endAt = model.endAt,
            patientId = model.patient.id.toInt(),
            specificMedicalWorkerId = model.specificMedicalWorker?.id?.toInt(),
            description = model.description,
            reminders = model.reminders
        )
    }

    fun toDataSourceModel(model: List<EventDataModel>): List<EventDataSourceModel> {
        return model.map { toDataSourceModel(it) }
    }

}
