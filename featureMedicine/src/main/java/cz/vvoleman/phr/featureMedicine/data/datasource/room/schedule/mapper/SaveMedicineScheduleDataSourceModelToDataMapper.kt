package cz.vvoleman.phr.featureMedicine.data.datasource.room.schedule.mapper

import cz.vvoleman.phr.featureMedicine.data.datasource.room.schedule.MedicineScheduleDataSourceModel
import cz.vvoleman.phr.featureMedicine.data.model.schedule.save.SaveMedicineScheduleDataModel

class SaveMedicineScheduleDataSourceModelToDataMapper {

    fun toDataSource(model: SaveMedicineScheduleDataModel): MedicineScheduleDataSourceModel {
        return MedicineScheduleDataSourceModel(
            id = model.id?.toInt() ?: 0,
            patientId = model.patient.id.toInt(),
            medicineId = model.medicine.id,
            createdAt = model.createdAt,
            problemCategoryId = model.problemCategory?.id?.toInt()
        )
    }
}
