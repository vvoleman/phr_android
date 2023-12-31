package cz.vvoleman.phr.common.presentation.mapper.healthcare

import cz.vvoleman.phr.common.domain.model.healthcare.AdditionalInfoDomainModel
import cz.vvoleman.phr.common.domain.model.healthcare.worker.MedicalWorkerDomainModel
import cz.vvoleman.phr.common.presentation.model.healthcare.core.AdditionalInfoPresentationModel
import cz.vvoleman.phr.common.presentation.model.healthcare.core.MedicalWorkerPresentationModel

class MedicalWorkerAdditionalInfoPresentationModelToDomainMapper(
    private val workerMapper: MedicalWorkerPresentationModelToDomainMapper
) {

    fun toDomain(
        model: AdditionalInfoPresentationModel<MedicalWorkerPresentationModel>
    ): AdditionalInfoDomainModel<MedicalWorkerDomainModel> {
        return AdditionalInfoDomainModel(
            icon = model.icon,
            text = model.text,
            onClick = model.onClick?.let {
                { worker: MedicalWorkerDomainModel ->
                    it(workerMapper.toPresentation(worker))
                }
            }
        )
    }

    fun toPresentation(
        model: AdditionalInfoDomainModel<MedicalWorkerDomainModel>
    ): AdditionalInfoPresentationModel<MedicalWorkerPresentationModel> {
        return AdditionalInfoPresentationModel(
            icon = model.icon,
            text = model.text,
            onClick = model.onClick?.let {
                { worker: MedicalWorkerPresentationModel ->
                    it(workerMapper.toDomain(worker))
                }
            }
        )
    }

    fun toPresentation(
        map: Map<MedicalWorkerDomainModel, List<AdditionalInfoDomainModel<MedicalWorkerDomainModel>>>
    ): Map<MedicalWorkerPresentationModel, List<AdditionalInfoPresentationModel<MedicalWorkerPresentationModel>>> {
        return map.mapKeys { (worker, _) ->
            workerMapper.toPresentation(worker)
        }.mapValues { (_, additions) ->
            additions.map { toPresentation(it) }
        }
    }

}
