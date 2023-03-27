package cz.vvoleman.phr.feature_medicalrecord.data.mapper

import cz.vvoleman.phr.feature_medicalrecord.data.datasource.model.room.asset.MedicalRecordAssetDataSourceModel
import cz.vvoleman.phr.feature_medicalrecord.domain.model.MedicalRecordAssetDomainModel

class MedicalRecordAssetDataSourceToDomainMapper {

    fun toDomain(medicalRecordAssetDataSourceModel: MedicalRecordAssetDataSourceModel): MedicalRecordAssetDomainModel {
        return MedicalRecordAssetDomainModel(
            id = medicalRecordAssetDataSourceModel.id.toString(),
            url = medicalRecordAssetDataSourceModel.uri,
            medicalRecordId = medicalRecordAssetDataSourceModel.medical_record_id.toString(),
            createdAt = medicalRecordAssetDataSourceModel.createdAt
        )
    }

}
