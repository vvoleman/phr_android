package cz.vvoleman.phr.common.data.mapper

import cz.vvoleman.phr.common.data.datasource.model.PatientDataSourceModel
import cz.vvoleman.phr.common.domain.model.AddEditPatientDomainModel

class PatientDataSourceModelToAddEditMapper {

    fun toDataSource(patient: AddEditPatientDomainModel) = PatientDataSourceModel(
        id = patient.id?.toInt(),
        name = patient.name,
        birth_date = patient.birthDate
    )
}
