package cz.vvoleman.phr.featureMedicalRecord.presentation.addEdit.mapper

import cz.vvoleman.phr.featureMedicalRecord.domain.model.PatientDomainModel
import cz.vvoleman.phr.featureMedicalRecord.presentation.addEdit.model.PatientPresentationModel

class PatientDomainModelToPresentationMapper {

    fun toPresentation(patient: PatientDomainModel): PatientPresentationModel {
        return PatientPresentationModel(
            id = patient.id,
            name = patient.name,
            birthDate = patient.birthDate
        )
    }
}
