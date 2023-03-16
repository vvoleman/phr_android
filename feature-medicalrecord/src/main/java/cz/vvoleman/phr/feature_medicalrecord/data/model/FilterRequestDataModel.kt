package cz.vvoleman.phr.feature_medicalrecord.data.model

data class FilterRequestDataModel(
    val sortDirection: String,
    val sortBy: String,
    val patientId: String,
    val selectedMedicalWorkerIds: List<String>,
    val selectedCategoryProblemIds: List<String>
)