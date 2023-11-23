package cz.vvoleman.phr.common.data.mapper.healthcare

import cz.vvoleman.phr.common.data.datasource.model.healthcare.service.MedicalServiceDataSourceModel
import cz.vvoleman.phr.common.domain.model.healthcare.service.MedicalServiceDomainModel

class MedicalServiceDataSourceModelToDomainMapper {

    fun toDomain(model: MedicalServiceDataSourceModel): MedicalServiceDomainModel {
        return MedicalServiceDomainModel(
            id = model.id!!.toString(),
            careField = model.careField,
            careForm = model.careForm,
            careType = model.careType,
            careExtent = model.careExtent,
            bedCount = model.bedCount,
            serviceNote = model.serviceNote,
            professionalRepresentative = model.professionalRepresentative,
            medicalFacilityId = model.medicalFacilityId
        )
    }

    fun toDataSource(model: MedicalServiceDomainModel): MedicalServiceDataSourceModel {
        return MedicalServiceDataSourceModel(
            id = model.id.toInt(),
            careField = model.careField,
            careForm = model.careForm,
            careType = model.careType,
            careExtent = model.careExtent,
            bedCount = model.bedCount,
            serviceNote = model.serviceNote,
            professionalRepresentative = model.professionalRepresentative,
            medicalFacilityId = model.medicalFacilityId
        )
    }
}
