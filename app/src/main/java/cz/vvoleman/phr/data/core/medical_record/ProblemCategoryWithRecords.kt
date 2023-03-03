package cz.vvoleman.phr.data.core.medical_record

data class ProblemCategoryWithRecords(
    val problemCategory: ProblemCategory,
    val records: List<MedicalRecord>
)
