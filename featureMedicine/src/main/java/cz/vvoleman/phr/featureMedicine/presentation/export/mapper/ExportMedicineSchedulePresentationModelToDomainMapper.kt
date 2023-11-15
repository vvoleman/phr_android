package cz.vvoleman.phr.featureMedicine.presentation.export.mapper

import cz.vvoleman.phr.common.presentation.mapper.PatientPresentationModelToDomainMapper
import cz.vvoleman.phr.featureMedicine.domain.model.export.ExportMedicineScheduleDomainModel
import cz.vvoleman.phr.featureMedicine.presentation.export.model.ExportMedicineSchedulePresentationModel
import cz.vvoleman.phr.featureMedicine.presentation.list.mapper.MedicinePresentationModelToDomainMapper

class ExportMedicineSchedulePresentationModelToDomainMapper(
    private val medicineMapper: MedicinePresentationModelToDomainMapper,
    private val scheduleItemMapper: ExportScheduleItemPresentationModelToDomainMapper,
    private val patientMapper: PatientPresentationModelToDomainMapper
) {

    fun toDomain(model: ExportMedicineSchedulePresentationModel): ExportMedicineScheduleDomainModel {
        return ExportMedicineScheduleDomainModel(
            id = model.id,
            medicine = medicineMapper.toDomain(model.medicine),
            patient = patientMapper.toDomain(model.patient),
            schedules = model.schedules.map { scheduleItemMapper.toDomain(it) },
        )
    }

    fun toPresentation(model: ExportMedicineScheduleDomainModel): ExportMedicineSchedulePresentationModel {
        return ExportMedicineSchedulePresentationModel(
            id = model.id,
            medicine = medicineMapper.toPresentation(model.medicine),
            patient = patientMapper.toPresentation(model.patient),
            schedules = model.schedules.map { scheduleItemMapper.toPresentation(it) },
        )
    }

}