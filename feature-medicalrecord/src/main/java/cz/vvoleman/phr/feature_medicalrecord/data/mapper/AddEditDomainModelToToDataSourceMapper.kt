package cz.vvoleman.phr.feature_medicalrecord.data.mapper

import cz.vvoleman.phr.feature_medicalrecord.data.datasource.model.room.MedicalRecordDataSourceModel
import cz.vvoleman.phr.feature_medicalrecord.domain.model.add_edit.AddEditDomainModel

class AddEditDomainModelToToDataSourceMapper {

    fun toDataSource(addEditDomainModel: AddEditDomainModel): MedicalRecordDataSourceModel {
        return MedicalRecordDataSourceModel(
            id = addEditDomainModel.id?.toInt(),
            patient_id = addEditDomainModel.patientId.toInt(),
            medical_worker_id = addEditDomainModel.medicalWorkerId?.toInt(),
            problem_category_id = addEditDomainModel.problemCategoryId?.toInt(),
            diagnose_id = addEditDomainModel.diagnoseId,
            visit_date = addEditDomainModel.visitDate,
            created_at = addEditDomainModel.createdAt
        )
    }
}
