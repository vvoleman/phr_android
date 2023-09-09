package cz.vvoleman.phr.feature_medicalrecord.data.mapper

import cz.vvoleman.phr.common.data.datasource.model.PatientDataSourceModel
import cz.vvoleman.phr.feature_medicalrecord.domain.model.PatientDomainModel

class PatientDataSourceToDomainMapper {

    fun toDomain(patient: PatientDataSourceModel): PatientDomainModel {
        return PatientDomainModel(
            id = patient.id.toString(),
            name = patient.name,
            birthDate = patient.birth_date
        )
    }
}
