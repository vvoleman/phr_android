package cz.vvoleman.phr.featureMedicalRecord.domain.model

import android.os.Parcelable
import cz.vvoleman.phr.common.domain.model.healthcare.worker.SpecificMedicalWorkerDomainModel
import cz.vvoleman.phr.common.domain.model.patient.PatientDomainModel
import cz.vvoleman.phr.common.domain.model.problemCategory.ProblemCategoryDomainModel
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
data class MedicalRecordDomainModel(
    val id: String,
    val patient: PatientDomainModel,
    val createdAt: LocalDate,
    val problemCategory: ProblemCategoryDomainModel? = null,
    val diagnose: DiagnoseDomainModel? = null,
    val visitDate: LocalDate,
    val specificMedicalWorker: SpecificMedicalWorkerDomainModel? = null,
    val assets: List<MedicalRecordAssetDomainModel> = emptyList()
) : Parcelable
