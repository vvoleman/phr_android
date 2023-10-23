package cz.vvoleman.phr.featureMedicine.data.mapper.schedule

import cz.vvoleman.phr.featureMedicine.data.mapper.medicine.MedicineDataModelToDomainMapper
import cz.vvoleman.phr.featureMedicine.data.model.schedule.save.SaveMedicineScheduleDataModel
import cz.vvoleman.phr.featureMedicine.domain.model.schedule.save.SaveMedicineScheduleDomainModel

class SaveMedicineScheduleDomainModelToDataMapper(
    private val medicineDataModelToDomainMapper: MedicineDataModelToDomainMapper,
    private val scheduleItemDataModelToDomainMapper: ScheduleItemDataModelToDomainMapper,
) {

    fun toData(model: SaveMedicineScheduleDomainModel): SaveMedicineScheduleDataModel {
        return SaveMedicineScheduleDataModel(
            id = model.id,
            patient = model.patient,
            medicine = medicineDataModelToDomainMapper.toData(model.medicine),
            createdAt = model.createdAt,
            schedules = model.schedules.map { scheduleItemDataModelToDomainMapper.toData(it) }
        )
    }
}
