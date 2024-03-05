package cz.vvoleman.phr.featureMedicalRecord.presentation.model.export

import cz.vvoleman.phr.featureMedicalRecord.presentation.model.core.MedicalRecordAssetPresentationModel
import cz.vvoleman.phr.featureMedicalRecord.presentation.model.core.MedicalRecordPresentationModel

data class ExportMedicalRecordParamsPresentationModel(
    val medicalRecord: MedicalRecordPresentationModel,
    val asset: MedicalRecordAssetPresentationModel
)
