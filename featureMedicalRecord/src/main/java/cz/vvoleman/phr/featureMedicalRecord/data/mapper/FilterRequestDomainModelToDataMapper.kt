package cz.vvoleman.phr.featureMedicalRecord.data.mapper

import cz.vvoleman.phr.featureMedicalRecord.data.model.FilterRequestDataModel
import cz.vvoleman.phr.featureMedicalRecord.domain.model.list.FilterRequestDomainModel
import cz.vvoleman.phr.featureMedicalRecord.domain.model.list.GroupByDomainModel

class FilterRequestDomainModelToDataMapper {

    fun toData(filterRequest: FilterRequestDomainModel): FilterRequestDataModel {
        return FilterRequestDataModel(
            sortDirection = filterRequest.sortBy.name,
            sortBy = getSortBy(filterRequest.groupBy),
            patientId = filterRequest.patientId!!,
            selectedMedicalWorkerIds = filterRequest.selectedMedicalWorkerIds,
            selectedCategoryProblemIds = filterRequest.selectedCategoryProblemIds
        )
    }

    private fun getSortBy(sortBy: GroupByDomainModel): String {
        return when (sortBy) {
            GroupByDomainModel.DATE -> "created_at"
            GroupByDomainModel.PROBLEM_CATEGORY -> "problem_category"
            GroupByDomainModel.MEDICAL_WORKER -> "medical_worker"
        }
    }
}
