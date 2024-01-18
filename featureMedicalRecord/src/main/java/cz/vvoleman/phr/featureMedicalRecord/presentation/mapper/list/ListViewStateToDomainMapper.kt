package cz.vvoleman.phr.featureMedicalRecord.presentation.mapper.list

import cz.vvoleman.phr.featureMedicalRecord.domain.model.list.FilterRequestDomainModel
import cz.vvoleman.phr.featureMedicalRecord.presentation.model.list.ListMedicalRecordViewState

class ListViewStateToDomainMapper {

    fun toDomain(viewState: ListMedicalRecordViewState): FilterRequestDomainModel {
        return FilterRequestDomainModel(
            groupBy = viewState.groupBy,
            sortBy = viewState.sortBy,
            patientId = null,
            selectedMedicalWorkerIds = viewState.selectedMedicalWorkers,
            selectedCategoryProblemIds = viewState.selectedProblemCategories
        )
    }
}
