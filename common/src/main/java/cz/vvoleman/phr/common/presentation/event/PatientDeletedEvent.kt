package cz.vvoleman.phr.common.presentation.event

import cz.vvoleman.phr.common.domain.model.patient.PatientDomainModel

data class PatientDeletedEvent(
    val patient: PatientDomainModel
)
