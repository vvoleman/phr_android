package cz.vvoleman.phr.common.ui.mapper.healthcare

import cz.vvoleman.phr.common.presentation.model.healthcare.core.MedicalWorkerPresentationModel
import cz.vvoleman.phr.common.ui.model.healthcare.core.MedicalWorkerUiModel

class MedicalWorkerUiModelToDomainMapper {

    fun toPresentation(model: MedicalWorkerUiModel): MedicalWorkerPresentationModel {
        return MedicalWorkerPresentationModel(
            id = model.id,
            name = model.name,
            patientId = model.patientId
        )
    }

    fun toUi(model: MedicalWorkerPresentationModel): MedicalWorkerUiModel {
        return MedicalWorkerUiModel(
            id = model.id,
            name = model.name,
            patientId = model.patientId
        )
    }
}
