package cz.vvoleman.phr.featureMedicalRecord.domain.model.list

data class FilterRequestDomainModel(
    val groupBy: GroupByDomainModel,
    val sortBy: SortByDomainModel,
    val patientId: String? = null,
    val selectedMedicalWorkerIds: List<String>,
    val selectedCategoryProblemIds: List<String>
)
