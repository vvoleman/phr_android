package cz.vvoleman.phr.feature_medicalrecord.domain.model.list

import cz.vvoleman.phr.feature_medicalrecord.domain.model.MedicalRecordDomainModel

data class GroupedMedicalRecordsDomainModel(
    val value: Any,
    val records: List<MedicalRecordDomainModel>
)
