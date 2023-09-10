package cz.vvoleman.phr.featureMedicalRecord.domain.model.list

import cz.vvoleman.phr.featureMedicalRecord.domain.model.MedicalRecordDomainModel

data class GroupedMedicalRecordsDomainModel(
    val value: Any,
    val records: List<MedicalRecordDomainModel>
)
