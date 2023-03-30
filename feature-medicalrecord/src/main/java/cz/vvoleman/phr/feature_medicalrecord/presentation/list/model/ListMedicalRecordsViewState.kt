package cz.vvoleman.phr.feature_medicalrecord.presentation.list.model

import android.os.Parcelable
import cz.vvoleman.phr.common.domain.GroupedItemsDomainModel
import cz.vvoleman.phr.feature_medicalrecord.domain.model.MedicalRecordDomainModel
import cz.vvoleman.phr.feature_medicalrecord.domain.model.MedicalWorkerDomainModel
import cz.vvoleman.phr.feature_medicalrecord.domain.model.ProblemCategoryDomainModel
import cz.vvoleman.phr.feature_medicalrecord.domain.model.list.GroupByDomainModel
import cz.vvoleman.phr.feature_medicalrecord.domain.model.list.SortByDomainModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class ListMedicalRecordsViewState(
    val groupedRecords: List<GroupedItemsDomainModel<MedicalRecordDomainModel>> = emptyList(),
    val isLoading: Boolean = false,
    val groupBy: GroupByDomainModel = GroupByDomainModel.DATE,
    val sortBy: SortByDomainModel = SortByDomainModel.DESC,
    val allProblemCategories: List<ProblemCategoryDomainModel> = emptyList(),
    val allMedicalWorkers: List<MedicalWorkerDomainModel> = emptyList(),
    val selectedProblemCategories: List<String> = emptyList(),
    val selectedMedicalWorkers: List<String> = emptyList(),
) : Parcelable