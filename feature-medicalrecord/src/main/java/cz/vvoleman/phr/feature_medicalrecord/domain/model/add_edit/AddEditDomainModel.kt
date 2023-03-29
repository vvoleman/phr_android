package cz.vvoleman.phr.feature_medicalrecord.domain.model.add_edit

import android.net.Uri
import cz.vvoleman.phr.feature_medicalrecord.domain.model.MedicalRecordAssetDomainModel
import java.time.LocalDate

data class AddEditDomainModel(
    val id: String? = null,
    val createdAt: LocalDate,
    val patientId: String,
    val problemCategoryId: String? = null,
    val medicalWorkerId: String? = null,
    val visitDate: LocalDate,
    val diagnoseId: String? = null,
    val files: List<AssetDomainModel> = listOf()
)