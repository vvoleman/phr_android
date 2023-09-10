package cz.vvoleman.phr.featureMedicine.data.mapper.schedule

import cz.vvoleman.phr.featureMedicine.data.mapper.medicine.MedicineDataModelToDomainMapper
import cz.vvoleman.phr.featureMedicine.data.model.schedule.MedicineScheduleDataModel
import cz.vvoleman.phr.featureMedicine.domain.model.schedule.MedicineScheduleDomainModel

class MedicineScheduleDataModelToDomainMapper(
    private val scheduleItemDataMapper: ScheduleItemDataModelToDomainMapper,
    private val medicineDataMapper: MedicineDataModelToDomainMapper
) {

    fun toDomain(model: MedicineScheduleDataModel): MedicineScheduleDomainModel {
        return MedicineScheduleDomainModel(
            id = model.id!!,
            patient = model.patient,
            medicine = medicineDataMapper.toDomain(model.medicine),
            schedules = model.schedules.map { scheduleItemDataMapper.toDomain(it) },
            createdAt = model.createdAt
        )
    }

    fun toData(model: MedicineScheduleDomainModel): MedicineScheduleDataModel {
        return MedicineScheduleDataModel(
            id = model.id,
            patient = model.patient,
            medicine = medicineDataMapper.toData(model.medicine),
            schedules = model.schedules.map { scheduleItemDataMapper.toData(it) },
            createdAt = model.createdAt
        )
    }
}
