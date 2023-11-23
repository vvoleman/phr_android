package cz.vvoleman.phr.common.data.mapper.healthcare

import cz.vvoleman.phr.common.data.datasource.model.healthcare.service.MedicalServiceWithInfoDataSourceModel
import cz.vvoleman.phr.common.domain.model.healthcare.service.MedicalServiceWithInfoDomainModel

class MedicalServiceWithInfoDataSourceModelToDomainMapper(
    private val serviceMapper: MedicalServiceDataSourceModelToDomainMapper,
) {

    fun toDomain(model: MedicalServiceWithInfoDataSourceModel): MedicalServiceWithInfoDomainModel {
        return MedicalServiceWithInfoDomainModel(
            medicalService = serviceMapper.toDomain(model.medicalService),
            email = model.email,
            telephone = model.telephone,
        )
    }

    fun toDataSource(model: MedicalServiceWithInfoDomainModel): MedicalServiceWithInfoDataSourceModel {
        return MedicalServiceWithInfoDataSourceModel(
            medicalService = serviceMapper.toDataSource(model.medicalService),
            email = model.email,
            telephone = model.telephone,
        )
    }
}
