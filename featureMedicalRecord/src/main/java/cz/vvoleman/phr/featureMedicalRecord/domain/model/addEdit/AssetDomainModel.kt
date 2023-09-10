package cz.vvoleman.phr.featureMedicalRecord.domain.model.addEdit

import java.time.LocalDate

data class AssetDomainModel(
    val id: String? = null,
    val createdAt: LocalDate = LocalDate.now(),
    val uri: String
)
