package cz.vvoleman.phr.feature_medicine.data.datasource.room.schedule.mapper

import cz.vvoleman.phr.common.data.mapper.PatientDataSourceModelToDomainMapper
import cz.vvoleman.phr.feature_medicine.data.datasource.room.medicine.mapper.MedicineDataSourceModelToDataMapper
import cz.vvoleman.phr.feature_medicine.data.datasource.room.schedule.MedicineScheduleDataSourceModel
import cz.vvoleman.phr.feature_medicine.data.datasource.room.schedule.ScheduleWithDetailsDataSourceModel
import cz.vvoleman.phr.feature_medicine.data.model.schedule.MedicineScheduleDataModel

class MedicineScheduleDataSourceModelToDataMapper (
    private val patientDataSourceMapper: PatientDataSourceModelToDomainMapper,
    private val medicineDataSourceMapper: MedicineDataSourceModelToDataMapper,
    private val scheduleItemDataSourceMapper: ScheduleItemDataSourceModelToDataMapper,
        ) {

    suspend fun toData(model: ScheduleWithDetailsDataSourceModel): MedicineScheduleDataModel {
        return MedicineScheduleDataModel(
            id = model.schedule.id.toString(),
            patient = patientDataSourceMapper.toDomain(model.patient),
            medicine = medicineDataSourceMapper.toData(model.medicine),
            schedules = model.items.map { scheduleItemDataSourceMapper.toData(it) },
            createdAt = model.schedule.createdAt
        )
    }

    fun toDataSource(model: MedicineScheduleDataModel): MedicineScheduleDataSourceModel {
        return MedicineScheduleDataSourceModel(
            id = model.id!!.toInt(),
            patient_id = model.patient.id.toInt(),
            medicine_id = model.medicine.id.toInt(),
            createdAt = model.createdAt
        )
    }

}