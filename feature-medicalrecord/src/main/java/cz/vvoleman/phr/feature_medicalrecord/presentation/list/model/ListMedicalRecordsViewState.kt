package cz.vvoleman.phr.feature_medicalrecord.presentation.list.model

import cz.vvoleman.phr.common.domain.GroupedItemsDomainModel
import cz.vvoleman.phr.feature_medicalrecord.domain.model.MedicalRecordDomainModel
import cz.vvoleman.phr.feature_medicalrecord.domain.model.list.GroupByDomainModel
import cz.vvoleman.phr.feature_medicalrecord.domain.model.list.SortByDomainModel

data class ListMedicalRecordsViewState(
    val groupedRecords: List<GroupedItemsDomainModel<MedicalRecordDomainModel>> = emptyList(),
    val isLoading: Boolean = false,

    val groupBy: GroupByDomainModel = GroupByDomainModel.DATE,
    val sortBy: SortByDomainModel = SortByDomainModel.DESC,
    val selectedProblemCategories: List<String> = emptyList(),
    val selectedMedicalWorkers: List<String> = emptyList(),
)