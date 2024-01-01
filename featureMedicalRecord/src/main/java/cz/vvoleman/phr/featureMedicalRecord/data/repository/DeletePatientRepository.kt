package cz.vvoleman.phr.featureMedicalRecord.data.repository

import android.util.Log
import cz.vvoleman.phr.common.data.datasource.model.healthcare.worker.MedicalWorkerDao
import cz.vvoleman.phr.common.data.datasource.model.problemCategory.ProblemCategoryDao
import cz.vvoleman.phr.featureMedicalRecord.data.datasource.model.room.MedicalRecordDao
import cz.vvoleman.phr.featureMedicalRecord.data.datasource.model.room.asset.MedicalRecordAssetDao
import cz.vvoleman.phr.featureMedicalRecord.domain.repository.patientDelete.DeleteMedicalRecordAssetsRepository
import cz.vvoleman.phr.featureMedicalRecord.domain.repository.patientDelete.DeleteMedicalRecordsRepository
import cz.vvoleman.phr.featureMedicalRecord.domain.repository.patientDelete.DeleteMedicalWorkersRepository
import cz.vvoleman.phr.featureMedicalRecord.domain.repository.patientDelete.DeleteProblemCategoriesRepository
import kotlinx.coroutines.flow.firstOrNull
import java.io.File

class DeletePatientRepository(
    private val medicalRecordDao: MedicalRecordDao,
    private val medicalWorkerDao: MedicalWorkerDao,
    private val medicalAssetDao: MedicalRecordAssetDao,
    private val problemCategoryDao: ProblemCategoryDao
) : DeleteMedicalRecordAssetsRepository,
    DeleteMedicalWorkersRepository,
    DeleteMedicalRecordsRepository,
    DeleteProblemCategoriesRepository {

    override suspend fun deleteMedicalRecordAssets(patientId: String) {
        val assets = medicalAssetDao.getByPatient(patientId).firstOrNull() ?: return

        assets.forEach {
            val file = File(it.uri)
            Log.d("DeletePatientRepository", "Deleting file ${file.absolutePath}")
            if (file.exists()) {
                Log.d("DeletePatientRepository", "File ${file.absolutePath} exists")
                file.delete()
            }
        }

        medicalAssetDao.deleteByPatient(patientId)
    }

    override suspend fun deleteMedicalRecords(patientId: String) {
        medicalRecordDao.deleteByPatient(patientId)
    }

    override suspend fun deleteMedicalWorkers(patientId: String) {
        medicalWorkerDao.deleteByPatient(patientId)
    }

    override suspend fun deleteProblemCategories(patientId: String) {
        problemCategoryDao.deleteByPatient(patientId)
    }
}
