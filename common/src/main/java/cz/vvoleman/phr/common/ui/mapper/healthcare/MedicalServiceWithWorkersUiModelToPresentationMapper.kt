package cz.vvoleman.phr.common.ui.mapper.healthcare

import cz.vvoleman.phr.common.presentation.model.healthcare.core.MedicalServiceWithWorkersPresentationModel
import cz.vvoleman.phr.common.ui.model.healthcare.core.MedicalServiceWithWorkersUiModel

class MedicalServiceWithWorkersUiModelToPresentationMapper(
    private val serviceMapper: MedicalServiceUiModelToPresentationMapper,
    private val workerInfoMapper: MedicalWorkerWithInfoUiModelToDomainMapper,
) {

    fun toPresentation(model: MedicalServiceWithWorkersUiModel): MedicalServiceWithWorkersPresentationModel {
        return MedicalServiceWithWorkersPresentationModel(
            medicalService = serviceMapper.toPresentation(model.medicalService),
            workers = model.workers.map { workerInfoMapper.toPresentation(it) }
        )
    }

    fun toUi(model: MedicalServiceWithWorkersPresentationModel): MedicalServiceWithWorkersUiModel {
        return MedicalServiceWithWorkersUiModel(
            medicalService = serviceMapper.toUi(model.medicalService),
            workers = model.workers.map { workerInfoMapper.toUi(it) }
        )
    }
}
