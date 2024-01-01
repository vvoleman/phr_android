package cz.vvoleman.phr.common.ui.mapper.healthcare

import cz.vvoleman.phr.common.presentation.model.healthcare.core.AdditionalInfoPresentationModel
import cz.vvoleman.phr.common.presentation.model.healthcare.core.MedicalFacilityPresentationModel
import cz.vvoleman.phr.common.ui.model.healthcare.core.AdditionalInfoUiModel
import cz.vvoleman.phr.common.ui.model.healthcare.core.MedicalFacilityUiModel
import cz.vvoleman.phr.common.ui.model.healthcare.core.MedicalFacilityWithAdditionalInfoUiModel

class MedicalFacilityAdditionalInfoUiModelToPresentationMapper(
    private val facilityMapper: MedicalFacilityUiModelToPresentationMapper,
) {

    fun toUi(model: AdditionalInfoPresentationModel<MedicalFacilityPresentationModel>): AdditionalInfoUiModel<MedicalFacilityUiModel> {
        return AdditionalInfoUiModel(
            icon = model.icon,
            text = model.text,
            onClick = model.onClick?.let {
                {
                        facility: MedicalFacilityUiModel ->
                    it(facilityMapper.toPresentation(facility))
                }
            }
        )
    }

    fun toUi(
        map:
        Map<MedicalFacilityPresentationModel, List<AdditionalInfoPresentationModel<MedicalFacilityPresentationModel>>>
    ): List<MedicalFacilityWithAdditionalInfoUiModel> {
        return map.map { (facility, info) ->
            MedicalFacilityWithAdditionalInfoUiModel(
                medicalFacility = facilityMapper.toUi(facility),
                info = info.map { toUi(it) }
            )
        }
    }
}
