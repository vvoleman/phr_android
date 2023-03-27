package cz.vvoleman.phr.feature_medicalrecord.domain.model.add_edit

import android.net.Uri
import java.time.LocalDate

data class AddEditDomainModel(
    val id: String? = null,
    val createdAt: LocalDate,
    val patientId: String,
    val problemCategoryId: String? = null,
    val medicalWorkerId: String? = null,
    val diagnoseId: String? = null,
    val files: List<Uri> = listOf()
)