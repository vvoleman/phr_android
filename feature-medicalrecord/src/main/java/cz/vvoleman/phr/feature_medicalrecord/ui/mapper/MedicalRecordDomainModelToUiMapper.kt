package cz.vvoleman.phr.feature_medicalrecord.ui.mapper

import cz.vvoleman.phr.feature_medicalrecord.domain.model.MedicalRecordDomainModel
import cz.vvoleman.phr.feature_medicalrecord.ui.model.MedicalRecordUiModel

class MedicalRecordDomainModelToUiMapper {

    fun toUi(model: MedicalRecordDomainModel): MedicalRecordUiModel {
        return MedicalRecordUiModel(
            id = model.id,
            patient = model.patient.name,
            createdAt = model.createdAt,
            problemCategoryName = model.problemCategory?.name,
            problemCategoryColor = model.problemCategory?.color,
            diagnoseId = model.diagnose?.id,
            diagnoseName = model.diagnose?.name,
            medicalWorker = model.medicalWorker?.name
        )
    }

}