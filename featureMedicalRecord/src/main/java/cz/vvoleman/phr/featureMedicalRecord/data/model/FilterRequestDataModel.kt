package cz.vvoleman.phr.featureMedicalRecord.data.model

data class FilterRequestDataModel(
    val sortDirection: String,
    val sortBy: String,
    val patientId: String,
    val selectedMedicalWorkerIds: List<String>,
    val selectedCategoryProblemIds: List<String>
)
