package cz.vvoleman.phr.feature_medicalrecord.domain.model.add_edit

import java.time.LocalDate

data class AssetDomainModel(
    val id: String? = null,
    val createdAt: LocalDate = LocalDate.now(),
    val uri: String
)
