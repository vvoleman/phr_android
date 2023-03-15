package cz.vvoleman.phr.feature_medicalrecord.ui.mapper

import cz.vvoleman.phr.common.domain.GroupedItemsDomainModel
import cz.vvoleman.phr.common.ui.model.GroupedItemsUiModel
import cz.vvoleman.phr.feature_medicalrecord.domain.model.MedicalRecordDomainModel
import cz.vvoleman.phr.feature_medicalrecord.ui.model.MedicalRecordUiModel

class GroupedItemsDomainModelToUiMapper(
    private val medicalRecordDomainModelToUiMapper: MedicalRecorDomainModelToUiMapper
) {

    fun toUi(model: GroupedItemsDomainModel<MedicalRecordDomainModel>): GroupedItemsUiModel<MedicalRecordUiModel> {
        return GroupedItemsUiModel(
            model.value,
            model.items.map { medicalRecordDomainModelToUiMapper.toUi(it) }
        )
    }

}