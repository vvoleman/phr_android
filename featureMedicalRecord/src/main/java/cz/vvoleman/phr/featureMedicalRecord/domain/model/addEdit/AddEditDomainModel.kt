package cz.vvoleman.phr.featureMedicalRecord.domain.model.addEdit

import cz.vvoleman.phr.featureMedicalRecord.domain.model.DiagnoseDomainModel
import java.time.LocalDate

data class AddEditDomainModel(
    val id: String? = null,
    val createdAt: LocalDate,
    val patientId: String,
    val problemCategoryId: String? = null,
    val specificMedicalWorkerId: String? = null,
    val visitDate: LocalDate,
    val diagnose: DiagnoseDomainModel? = null,
    val files: List<AssetDomainModel> = listOf()
)
