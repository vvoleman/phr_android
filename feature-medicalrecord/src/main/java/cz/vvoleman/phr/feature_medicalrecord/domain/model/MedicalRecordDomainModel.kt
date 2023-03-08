package cz.vvoleman.phr.feature_medicalrecord.domain.model
import java.time.LocalDate

data class MedicalRecordDomainModel(
    val id: String,
    val patient: PatientDomainModel,
    val createdAt: LocalDate,
    val problemCategory: ProblemCategoryDomainModel? = null,
    val diagnose: DiagnoseDomainModel? = null,
    val medicalWorker: MedicalWorkerDomainModel? = null
)
