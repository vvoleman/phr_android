package cz.vvoleman.phr.common.data.mapper

import cz.vvoleman.phr.common.data.datasource.model.PatientDataSourceModel
import cz.vvoleman.phr.common.domain.model.PatientDomainModel

class PatientDataSourceModelToDomainMapper {

    fun toDataSource(patient: PatientDomainModel): PatientDataSourceModel {
        return PatientDataSourceModel(
            id = patient.id.toInt(),
            name = patient.name,
            birth_date = patient.birthDate
        )
    }

    fun toDomain(patient: PatientDataSourceModel): PatientDomainModel {
        return PatientDomainModel(
            id = patient.id.toString(),
            name = patient.name,
            birthDate = patient.birth_date
        )
    }
}
