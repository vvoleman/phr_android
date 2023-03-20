package cz.vvoleman.phr.feature_medicalrecord.domain.model
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
data class MedicalRecordDomainModel(
    val id: String,
    val patient: PatientDomainModel,
    val createdAt: LocalDate,
    val problemCategory: ProblemCategoryDomainModel? = null,
    val diagnose: DiagnoseDomainModel? = null,
    val medicalWorker: MedicalWorkerDomainModel? = null
) : Parcelable
