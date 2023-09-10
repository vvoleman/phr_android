package cz.vvoleman.phr.featureMedicalRecord.data.mapper

import cz.vvoleman.phr.common.data.datasource.model.PatientDataSourceModel
import cz.vvoleman.phr.featureMedicalRecord.domain.model.PatientDomainModel

class PatientDataSourceModelToDomainMapper {

    fun toDomain(patient: PatientDataSourceModel): PatientDomainModel {
        return PatientDomainModel(
            id = patient.id.toString(),
            name = patient.name,
            birthDate = patient.birthDate
        )
    }
}
