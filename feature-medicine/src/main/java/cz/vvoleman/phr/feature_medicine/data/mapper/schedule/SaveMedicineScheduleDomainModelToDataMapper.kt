package cz.vvoleman.phr.feature_medicine.data.mapper.schedule

import cz.vvoleman.phr.feature_medicine.data.mapper.medicine.MedicineDataModelToDomainMapper
import cz.vvoleman.phr.feature_medicine.data.model.schedule.MedicineScheduleDataModel
import cz.vvoleman.phr.feature_medicine.domain.model.schedule.save.SaveMedicineScheduleDomainModel

class SaveMedicineScheduleDomainModelToDataMapper(
    private val medicineDataModelToDomainMapper: MedicineDataModelToDomainMapper,
    private val saveScheduleItemDomainModelToDataMapper: SaveScheduleItemDomainModelToDataMapper
) {

    fun toData(model: SaveMedicineScheduleDomainModel): MedicineScheduleDataModel {
        return MedicineScheduleDataModel(
            id = model.id,
            patient = model.patient,
            medicine = medicineDataModelToDomainMapper.toData(model.medicine),
            createdAt = model.createdAt,
            schedules = model.schedules.map { saveScheduleItemDomainModelToDataMapper.toData(it) }
        )
    }

}