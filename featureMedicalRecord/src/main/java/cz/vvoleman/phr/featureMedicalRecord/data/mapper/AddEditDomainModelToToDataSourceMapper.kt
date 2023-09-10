package cz.vvoleman.phr.featureMedicalRecord.data.mapper

import cz.vvoleman.phr.featureMedicalRecord.data.datasource.model.room.MedicalRecordDataSourceModel
import cz.vvoleman.phr.featureMedicalRecord.domain.model.addEdit.AddEditDomainModel

class AddEditDomainModelToToDataSourceMapper {

    fun toDataSource(addEditDomainModel: AddEditDomainModel): MedicalRecordDataSourceModel {
        return MedicalRecordDataSourceModel(
            id = addEditDomainModel.id?.toInt(),
            patientId = addEditDomainModel.patientId.toInt(),
            medicalWorkerId = addEditDomainModel.medicalWorkerId?.toInt(),
            problemCategoryId = addEditDomainModel.problemCategoryId?.toInt(),
            diagnoseId = addEditDomainModel.diagnoseId,
            visitDate = addEditDomainModel.visitDate,
            createdAt = addEditDomainModel.createdAt
        )
    }
}
