package cz.vvoleman.phr.featureMedicalRecord.presentation.list.mapper

import cz.vvoleman.phr.featureMedicalRecord.domain.model.list.FilterRequestDomainModel
import cz.vvoleman.phr.featureMedicalRecord.presentation.list.model.ListMedicalRecordsViewState

class ListViewStateToDomainMapper {

    fun toDomain(viewState: ListMedicalRecordsViewState): FilterRequestDomainModel {
        return FilterRequestDomainModel(
            groupBy = viewState.groupBy,
            sortBy = viewState.sortBy,
            patientId = null,
            selectedMedicalWorkerIds = viewState.selectedMedicalWorkers,
            selectedCategoryProblemIds = viewState.selectedProblemCategories
        )
    }
}
