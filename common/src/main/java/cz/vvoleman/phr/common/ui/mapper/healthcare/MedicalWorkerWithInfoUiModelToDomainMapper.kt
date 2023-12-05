package cz.vvoleman.phr.common.ui.mapper.healthcare

import cz.vvoleman.phr.common.presentation.model.healthcare.core.MedicalWorkerWithInfoPresentationModel
import cz.vvoleman.phr.common.ui.model.healthcare.core.MedicalWorkerWithInfoUiModel

class MedicalWorkerWithInfoUiModelToDomainMapper(
    private val workerMapper: MedicalWorkerUiModelToDomainMapper,
) {

    fun toPresentation(model: MedicalWorkerWithInfoUiModel): MedicalWorkerWithInfoPresentationModel {
        return MedicalWorkerWithInfoPresentationModel(
            medicalWorker = workerMapper.toPresentation(model.medicalWorker),
            email = model.email,
            telephone = model.telephone,
        )
    }

    fun toUi(model: MedicalWorkerWithInfoPresentationModel): MedicalWorkerWithInfoUiModel {
        return MedicalWorkerWithInfoUiModel(
            medicalWorker = workerMapper.toUi(model.medicalWorker),
            email = model.email,
            telephone = model.telephone,
        )
    }
}
