package cz.vvoleman.phr.featureMedicalRecord.data.mapper

import cz.vvoleman.phr.featureMedicalRecord.data.datasource.model.room.MedicalRecordDataSourceModel
import cz.vvoleman.phr.featureMedicalRecord.domain.model.addEdit.AddEditDomainModel

class AddEditDomainModelToToDataSourceMapper {

    fun toDataSource(addEditDomainModel: AddEditDomainModel): MedicalRecordDataSourceModel {
        return MedicalRecordDataSourceModel(
            id = addEditDomainModel.id?.toInt(),
            patientId = addEditDomainModel.patientId.toInt(),
            specificMedicalWorkerId = addEditDomainModel.specificMedicalWorkerId?.toInt(),
            problemCategoryId = addEditDomainModel.problemCategoryId?.toInt(),
            diagnoseId = addEditDomainModel.diagnose?.id,
            visitDate = addEditDomainModel.visitDate,
            createdAt = addEditDomainModel.createdAt
        )
    }
}
