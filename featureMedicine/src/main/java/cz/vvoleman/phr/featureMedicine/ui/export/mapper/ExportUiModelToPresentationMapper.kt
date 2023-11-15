package cz.vvoleman.phr.featureMedicine.ui.export.mapper

import cz.vvoleman.phr.featureMedicine.presentation.export.model.ExportMedicineSchedulePresentationModel
import cz.vvoleman.phr.featureMedicine.ui.export.model.ExportMedicineScheduleUiModel
import cz.vvoleman.phr.featureMedicine.ui.export.model.ExportUiModel

class ExportUiModelToPresentationMapper {

    fun toUi(model: ExportMedicineSchedulePresentationModel): List<ExportUiModel>{
        return model.schedules.map {
            ExportUiModel(
                scheduleItemId = it.id,
                dateTime = it.dateTime,
                quantity = it.quantity,
                unit = it.unit,
                medicineId = model.medicine.id,
                medicineName = model.medicine.name,
                patientId = model.patient.id,
                patientName = model.patient.name,
            )
        }
    }

}