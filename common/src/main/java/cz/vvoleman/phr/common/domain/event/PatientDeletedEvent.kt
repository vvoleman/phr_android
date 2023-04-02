package cz.vvoleman.phr.common.domain.event

import cz.vvoleman.phr.common.domain.model.PatientDomainModel

data class PatientDeletedEvent(
    val patient: PatientDomainModel
)
