package cz.vvoleman.phr.common.ui.mapper.healthcare

import cz.vvoleman.phr.common.presentation.model.healthcare.core.AdditionalInfoPresentationModel
import cz.vvoleman.phr.common.presentation.model.healthcare.core.MedicalWorkerPresentationModel
import cz.vvoleman.phr.common.ui.model.healthcare.core.AdditionalInfoUiModel
import cz.vvoleman.phr.common.ui.model.healthcare.core.MedicalWorkerUiModel
import cz.vvoleman.phr.common.ui.model.healthcare.core.MedicalWorkerWithAdditionalInfoUiModel

class MedicalWorkerAdditionalInfoUiModelToPresentationMapper(
    private val workerMapper: MedicalWorkerUiModelToPresentationMapper
) {

    fun toPresentation(
        model: AdditionalInfoUiModel<MedicalWorkerUiModel>
    ): AdditionalInfoPresentationModel<MedicalWorkerPresentationModel> {
        return AdditionalInfoPresentationModel(
            icon = model.icon,
            text = model.text,
            onClick = model.onClick?.let {
                {
                        worker: MedicalWorkerPresentationModel ->
                    it(workerMapper.toUi(worker))
                }
            }
        )
    }

    fun toUi(
        model: AdditionalInfoPresentationModel<MedicalWorkerPresentationModel>
    ): AdditionalInfoUiModel<MedicalWorkerUiModel> {
        return AdditionalInfoUiModel(
            icon = model.icon,
            text = model.text,
            onClick = model.onClick?.let {
                {
                        worker: MedicalWorkerUiModel ->
                    it(workerMapper.toPresentation(worker))
                }
            }
        )
    }

    fun toUi(
        map: Map<MedicalWorkerPresentationModel, List<AdditionalInfoPresentationModel<MedicalWorkerPresentationModel>>>
    ): List<MedicalWorkerWithAdditionalInfoUiModel> {
        return map.map { (worker, info) ->
            MedicalWorkerWithAdditionalInfoUiModel(
                medicalWorker = workerMapper.toUi(worker),
                info = info.map { toUi(it) }
            )
        }
    }
}
