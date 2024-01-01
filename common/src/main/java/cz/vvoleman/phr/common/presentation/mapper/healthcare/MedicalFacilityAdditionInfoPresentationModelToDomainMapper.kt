package cz.vvoleman.phr.common.presentation.mapper.healthcare

import cz.vvoleman.phr.common.domain.model.healthcare.AdditionalInfoDomainModel
import cz.vvoleman.phr.common.domain.model.healthcare.facility.MedicalFacilityDomainModel
import cz.vvoleman.phr.common.presentation.model.healthcare.core.AdditionalInfoPresentationModel
import cz.vvoleman.phr.common.presentation.model.healthcare.core.MedicalFacilityPresentationModel

class MedicalFacilityAdditionInfoPresentationModelToDomainMapper(
    private val facilityMapper: MedicalFacilityPresentationModelToDomainMapper
) {

    fun toPresentation(
        model: AdditionalInfoDomainModel<MedicalFacilityDomainModel>
    ): AdditionalInfoPresentationModel<MedicalFacilityPresentationModel> {
        return AdditionalInfoPresentationModel(
            icon = model.icon,
            text = model.text,
            onClick = model.onClick?.let {
                {
                        facility: MedicalFacilityPresentationModel ->
                    it(facilityMapper.toDomain(facility))
                }
            }
        )
    }

    fun toPresentation(
        map: Map<MedicalFacilityDomainModel, List<AdditionalInfoDomainModel<MedicalFacilityDomainModel>>>
    ): Map<MedicalFacilityPresentationModel, List<AdditionalInfoPresentationModel<MedicalFacilityPresentationModel>>> {
        return map.mapKeys { (facility, _) ->
            facilityMapper.toPresentation(facility)
        }.mapValues { (_, additions) ->
            additions.map { toPresentation(it) }
        }
    }
}
