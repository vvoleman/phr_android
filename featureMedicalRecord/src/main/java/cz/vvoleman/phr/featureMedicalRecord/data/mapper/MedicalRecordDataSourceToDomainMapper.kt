package cz.vvoleman.phr.featureMedicalRecord.data.mapper

import cz.vvoleman.phr.common.data.datasource.model.healthcare.worker.SpecificMedicalWorkerDao
import cz.vvoleman.phr.common.data.mapper.healthcare.SpecificMedicalWorkerDataSourceToDomainMapper
import cz.vvoleman.phr.featureMedicalRecord.data.datasource.model.room.MedicalRecordWithDetails
import cz.vvoleman.phr.featureMedicalRecord.domain.model.MedicalRecordDomainModel
import kotlinx.coroutines.flow.first

class MedicalRecordDataSourceToDomainMapper(
    private val patientMapper: PatientDataSourceModelToDomainMapper,
    private val diagnoseMapper: DiagnoseDataSourceToDomainMapper,
    private val specificWorkerMapper: SpecificMedicalWorkerDataSourceToDomainMapper,
    private val problemCategoryMapper: ProblemCategoryDataSourceToDomainMapper,
    private val specificWorkerDao: SpecificMedicalWorkerDao,
) {

    suspend fun toDomain(medicalRecord: MedicalRecordWithDetails): MedicalRecordDomainModel {
        return MedicalRecordDomainModel(
            id = medicalRecord.medicalRecord.id.toString(),
            patient = patientMapper.toDomain(medicalRecord.patient),
            createdAt = medicalRecord.medicalRecord.createdAt,
            problemCategory = medicalRecord.problemCategory?.let { problemCategoryMapper.toDomain(it) },
            diagnose = medicalRecord.diagnose?.let { diagnoseMapper.toDomain(it) },
            specificMedicalWorker = medicalRecord.specificMedicalWorker?.let {
                specificWorkerMapper.toDomain(
                    specificWorkerDao.getById(it.id!!).first()
                )
            },
            assets = medicalRecord.assets.map { MedicalRecordAssetDataSourceToDomainMapper().toDomain(it) },
            visitDate = medicalRecord.medicalRecord.visitDate
        )
    }
}
