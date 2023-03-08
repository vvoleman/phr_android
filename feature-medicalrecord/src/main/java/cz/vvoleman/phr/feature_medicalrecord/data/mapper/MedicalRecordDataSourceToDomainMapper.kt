package cz.vvoleman.phr.feature_medicalrecord.data.mapper

import cz.vvoleman.phr.feature_medicalrecord.data.datasource.model.room.MedicalRecordWithDetails
import cz.vvoleman.phr.feature_medicalrecord.domain.model.MedicalRecordDomainModel

class MedicalRecordDataSourceToDomainMapper(
    private val patientMapper: PatientDataSourceToDomainMapper,
    private val diagnoseMapper: DiagnoseDataSourceToDomainMapper,
    private val medicalWorkerMapper: MedicalWorkerDataSourceToDomainMapper,
    private val problemCategoryMapper: ProblemCategoryDataSourceToDomainMapper
) {

    fun toDomain(medicalRecord: MedicalRecordWithDetails): MedicalRecordDomainModel {
        return MedicalRecordDomainModel(
            id = medicalRecord.medicalRecord.id.toString(),
            patient = patientMapper.toDomain(medicalRecord.patient),
            createdAt = medicalRecord.medicalRecord.created_at,
            problemCategory = medicalRecord.problemCategory?.let { ProblemCategoryDataSourceToDomainMapper().toDomain(it) },
            diagnose = medicalRecord.diagnose?.let { diagnoseMapper.toDomain(it) },
            medicalWorker = medicalRecord.medicalWorker?.let { medicalWorkerMapper.toDomain(it) }
        )
    }

}