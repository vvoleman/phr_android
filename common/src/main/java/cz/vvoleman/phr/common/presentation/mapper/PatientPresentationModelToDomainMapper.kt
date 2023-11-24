package cz.vvoleman.phr.common.presentation.mapper

import cz.vvoleman.phr.common.domain.model.PatientDomainModel
import cz.vvoleman.phr.common.presentation.model.patient.PatientPresentationModel

class PatientPresentationModelToDomainMapper {

    fun toDomain(patient: PatientPresentationModel): PatientDomainModel {
        return PatientDomainModel(
            id = patient.id,
            name = patient.name,
            birthDate = patient.birthDate
        )
    }

    fun toPresentation(patient: PatientDomainModel): PatientPresentationModel {
        return PatientPresentationModel(
            id = patient.id,
            name = patient.name,
            birthDate = patient.birthDate
        )
    }
}
