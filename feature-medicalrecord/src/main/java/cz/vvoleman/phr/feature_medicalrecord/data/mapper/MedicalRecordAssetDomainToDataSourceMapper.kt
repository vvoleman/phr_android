package cz.vvoleman.phr.feature_medicalrecord.data.mapper

import cz.vvoleman.phr.feature_medicalrecord.data.datasource.model.room.asset.MedicalRecordAssetDataSourceModel
import cz.vvoleman.phr.feature_medicalrecord.domain.model.MedicalRecordAssetDomainModel
import cz.vvoleman.phr.feature_medicalrecord.domain.model.select_file.AddMedicalRecordAssetDomainModel

class MedicalRecordAssetDomainToDataSourceMapper {

    fun toDataSource(model: AddMedicalRecordAssetDomainModel): MedicalRecordAssetDataSourceModel {
        return MedicalRecordAssetDataSourceModel(
            uri = model.url,
            medical_record_id = model.medicalRecordId.toInt(),
            createdAt = model.createdAt
        )
    }

}