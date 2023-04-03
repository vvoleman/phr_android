package cz.vvoleman.phr.common.domain.mapper

import cz.vvoleman.phr.common.domain.model.AddEditPatientDomainModel
import cz.vvoleman.phr.common.domain.model.PatientDomainModel

class PatientDomainModelToAddEditMapper {

    fun toAddEdit(patient: PatientDomainModel) = AddEditPatientDomainModel(
        id = patient.id,
        name = patient.name,
        birthDate = patient.birthDate
    )

}