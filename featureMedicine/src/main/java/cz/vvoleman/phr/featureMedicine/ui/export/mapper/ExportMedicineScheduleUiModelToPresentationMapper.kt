package cz.vvoleman.phr.featureMedicine.ui.export.mapper

import cz.vvoleman.phr.common.ui.mapper.PatientUiModelToPresentationMapper
import cz.vvoleman.phr.featureMedicine.presentation.export.model.ExportMedicineSchedulePresentationModel
import cz.vvoleman.phr.featureMedicine.ui.export.model.ExportMedicineScheduleUiModel
import cz.vvoleman.phr.featureMedicine.ui.list.mapper.MedicineUiModelToPresentationMapper

class ExportMedicineScheduleUiModelToPresentationMapper (
    private val medicineMapper: MedicineUiModelToPresentationMapper,
    private val patientMapper: PatientUiModelToPresentationMapper,
    private val scheduleItemMapper: ExportScheduleItemUiModelToPresentationMapper
){

    fun toPresentation(model: ExportMedicineScheduleUiModel): ExportMedicineSchedulePresentationModel {
        return ExportMedicineSchedulePresentationModel(
            id = model.id,
            medicine = medicineMapper.toPresentation(model.medicine),
            patient = patientMapper.toPresentation(model.patient),
            schedules = model.schedules.map { scheduleItemMapper.toPresentation(it) },
        )
    }

    fun toUi(model: ExportMedicineSchedulePresentationModel): ExportMedicineScheduleUiModel {
        return ExportMedicineScheduleUiModel(
            id = model.id,
            medicine = medicineMapper.toUi(model.medicine),
            patient = patientMapper.toUi(model.patient),
            schedules = model.schedules.map { scheduleItemMapper.toUi(it) },
        )
    }

}