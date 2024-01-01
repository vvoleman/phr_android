package cz.vvoleman.phr.common.domain.mapper.patient

import cz.vvoleman.phr.common.domain.model.patient.AddEditPatientDomainModel
import cz.vvoleman.phr.common.domain.model.patient.PatientDomainModel

class PatientDomainModelToAddEditMapper {

    fun toAddEdit(patient: PatientDomainModel) = AddEditPatientDomainModel(
        id = patient.id,
        name = patient.name,
        birthDate = patient.birthDate
    )
}
