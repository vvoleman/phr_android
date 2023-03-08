package cz.vvoleman.phr.feature_medicalrecord.domain.model.list

data class FilterRequestDomainModel(
    val groupBy: GroupByDomainModel,
    val sortBy: SortByDomainModel,
    val selectedMedicalWorkerIds: List<String>,
    val selectedCategoryProblemIds: List<String>
)
