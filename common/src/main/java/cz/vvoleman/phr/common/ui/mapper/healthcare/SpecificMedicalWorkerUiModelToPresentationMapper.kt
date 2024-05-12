package cz.vvoleman.phr.common.ui.mapper.healthcare

import cz.vvoleman.phr.common.presentation.model.healthcare.core.SpecificMedicalWorkerPresentationModel
import cz.vvoleman.phr.common.ui.model.healthcare.core.SpecificMedicalWorkerUiModel

class SpecificMedicalWorkerUiModelToPresentationMapper(
    private val medicalWorkerMapper: MedicalWorkerUiModelToPresentationMapper,
    private val medicalServiceMapper: MedicalServiceUiModelToPresentationMapper
) {

    fun toPresentation(model: SpecificMedicalWorkerUiModel): SpecificMedicalWorkerPresentationModel {
        return SpecificMedicalWorkerPresentationModel(
            id = model.id,
            telephone = model.telephone,
            email = model.email,
            medicalWorker = model.medicalWorker?.let { medicalWorkerMapper.toPresentation(it) },
            medicalService = model.medicalService?.let { medicalServiceMapper.toPresentation(it) }
        )
    }

    fun toPresentation(models: List<SpecificMedicalWorkerUiModel>): List<SpecificMedicalWorkerPresentationModel> {
        return models.map { toPresentation(it) }
    }

    fun toUi(model: SpecificMedicalWorkerPresentationModel): SpecificMedicalWorkerUiModel {
        return SpecificMedicalWorkerUiModel(
            id = model.id,
            telephone = model.telephone,
            email = model.email,
            medicalWorker = model.medicalWorker?.let { medicalWorkerMapper.toUi(it) },
            medicalService = model.medicalService?.let { medicalServiceMapper.toUi(it) }
        )
    }

    fun toUi(models: List<SpecificMedicalWorkerPresentationModel>): List<SpecificMedicalWorkerUiModel> {
        return models.map { toUi(it) }
    }
}
