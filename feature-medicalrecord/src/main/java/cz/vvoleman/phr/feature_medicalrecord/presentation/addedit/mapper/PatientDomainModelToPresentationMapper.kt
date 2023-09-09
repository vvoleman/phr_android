package cz.vvoleman.phr.feature_medicalrecord.presentation.addedit.mapper

import cz.vvoleman.phr.feature_medicalrecord.domain.model.PatientDomainModel
import cz.vvoleman.phr.feature_medicalrecord.presentation.addedit.model.PatientPresentationModel

class PatientDomainModelToPresentationMapper {

    fun toPresentation(patient: PatientDomainModel): PatientPresentationModel {
        return PatientPresentationModel(
            id = patient.id,
            name = patient.name,
            birthDate = patient.birthDate
        )
    }
}
