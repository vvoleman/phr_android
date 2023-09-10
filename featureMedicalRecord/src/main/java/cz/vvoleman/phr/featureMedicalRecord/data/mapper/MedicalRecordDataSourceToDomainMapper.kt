package cz.vvoleman.phr.featureMedicalRecord.data.mapper

import cz.vvoleman.phr.featureMedicalRecord.data.datasource.model.room.MedicalRecordWithDetails
import cz.vvoleman.phr.featureMedicalRecord.domain.model.MedicalRecordDomainModel

class MedicalRecordDataSourceToDomainMapper(
    private val patientMapper: PatientDataSourceModelToDomainMapper,
    private val diagnoseMapper: DiagnoseDataSourceToDomainMapper,
    private val medicalWorkerMapper: MedicalWorkerDataSourceToDomainMapper,
    private val problemCategoryMapper: ProblemCategoryDataSourceToDomainMapper
) {

    fun toDomain(medicalRecord: MedicalRecordWithDetails): MedicalRecordDomainModel {
        return MedicalRecordDomainModel(
            id = medicalRecord.medicalRecord.id.toString(),
            patient = patientMapper.toDomain(medicalRecord.patient),
            createdAt = medicalRecord.medicalRecord.createdAt,
            problemCategory = medicalRecord.problemCategory?.let { problemCategoryMapper.toDomain(it) },
            diagnose = medicalRecord.diagnose?.let { diagnoseMapper.toDomain(it) },
            medicalWorker = medicalRecord.medicalWorker?.let { medicalWorkerMapper.toDomain(it) },
            assets = medicalRecord.assets.map { MedicalRecordAssetDataSourceToDomainMapper().toDomain(it) },
            visitDate = medicalRecord.medicalRecord.visitDate
        )
    }
}
