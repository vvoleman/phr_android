package cz.vvoleman.phr.featureMedicalRecord.data.mapper

import cz.vvoleman.phr.featureMedicalRecord.data.datasource.model.room.asset.MedicalRecordAssetDataSourceModel
import cz.vvoleman.phr.featureMedicalRecord.domain.model.selectFile.AddMedicalRecordAssetDomainModel

class MedicalRecordAssetDomainToDataSourceMapper {

    fun toDataSource(model: AddMedicalRecordAssetDomainModel): MedicalRecordAssetDataSourceModel {
        return MedicalRecordAssetDataSourceModel(
            uri = model.url,
            medicalRecordId = model.medicalRecordId.toInt(),
            createdAt = model.createdAt
        )
    }
}
