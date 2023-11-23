package cz.vvoleman.phr.featureMedicalRecord.ui.mapper

import cz.vvoleman.phr.featureMedicalRecord.domain.model.MedicalRecordDomainModel
import cz.vvoleman.phr.featureMedicalRecord.ui.model.MedicalRecordUiModel

class MedicalRecordDomainModelToUiMapper {

    fun toUi(model: MedicalRecordDomainModel): MedicalRecordUiModel {
        return MedicalRecordUiModel(
            id = model.id,
            patient = model.patient.name,
            createdAt = model.createdAt,
            visitDate = model.visitDate,
            problemCategoryName = model.problemCategory?.name,
            problemCategoryColor = model.problemCategory?.color,
            diagnoseId = model.diagnose?.id,
            diagnoseName = model.diagnose?.name,
            medicalWorker = model.specificMedicalWorker?.medicalWorker?.name
        )
    }
}
