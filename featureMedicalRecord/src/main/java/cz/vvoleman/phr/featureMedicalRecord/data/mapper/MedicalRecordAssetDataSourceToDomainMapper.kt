package cz.vvoleman.phr.featureMedicalRecord.data.mapper

import cz.vvoleman.phr.featureMedicalRecord.data.datasource.model.room.asset.MedicalRecordAssetDataSourceModel
import cz.vvoleman.phr.featureMedicalRecord.domain.model.MedicalRecordAssetDomainModel

class MedicalRecordAssetDataSourceToDomainMapper {

    fun toDomain(medicalRecordAssetDataSourceModel: MedicalRecordAssetDataSourceModel): MedicalRecordAssetDomainModel {
        return MedicalRecordAssetDomainModel(
            id = medicalRecordAssetDataSourceModel.id.toString(),
            url = medicalRecordAssetDataSourceModel.uri,
            medicalRecordId = medicalRecordAssetDataSourceModel.medicalRecordId.toString(),
            createdAt = medicalRecordAssetDataSourceModel.createdAt
        )
    }
}
