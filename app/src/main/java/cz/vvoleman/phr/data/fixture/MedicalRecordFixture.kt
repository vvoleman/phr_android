package cz.vvoleman.phr.data.fixture

import cz.vvoleman.phr.common.data.datasource.model.PatientDataSourceModel
import cz.vvoleman.phr.feature_medicalrecord.data.datasource.model.room.MedicalRecordDao
import cz.vvoleman.phr.feature_medicalrecord.data.datasource.model.room.MedicalRecordDataSourceModel
import cz.vvoleman.phr.feature_medicalrecord.data.datasource.model.room.category.ProblemCategoryDataSourceModel
import cz.vvoleman.phr.feature_medicalrecord.data.datasource.model.room.diagnose.DiagnoseDataSourceModel
import cz.vvoleman.phr.feature_medicalrecord.data.datasource.model.room.worker.MedicalWorkerDataSourceModel
import java.time.LocalDate

class MedicalRecordFixture(
    private val medicalRecordDao: MedicalRecordDao,
    private val patients: List<PatientDataSourceModel>,
    private val diagnoses: List<DiagnoseDataSourceModel>,
    private val problemCategories: List<ProblemCategoryDataSourceModel>,
    private val medicalWorkers: List<MedicalWorkerDataSourceModel>
) {

    suspend fun setup(): List<MedicalRecordDataSourceModel> {
        val medicalRecords = getData()

        medicalRecords.forEach {
            medicalRecordDao.insert(it)
        }

        return medicalRecords
    }

    @Suppress("MagicNumber")
    private fun getData(): List<MedicalRecordDataSourceModel> {
        val dateA = LocalDate.of(2023, 1, 1)
        val dateB = LocalDate.of(2023, 1, 1)
        val dateC = LocalDate.of(2023, 2, 1)
        val dateD = LocalDate.of(2023, 3, 1)

        return listOf(
            MedicalRecordDataSourceModel(
                id = 1,
                patient_id = patients[0].id!!,
                diagnose_id = diagnoses[0].id,
                problem_category_id = problemCategories[0].id,
                medical_worker_id = medicalWorkers[0].id,
                created_at = dateA,
                visit_date = dateA
            ),
            MedicalRecordDataSourceModel(
                id = 2,
                patient_id = patients[0].id!!,
                diagnose_id = diagnoses[1].id,
                problem_category_id = problemCategories[0].id,
                medical_worker_id = medicalWorkers[1].id,
                created_at = dateB,
                visit_date = dateB
            ),
            MedicalRecordDataSourceModel(
                id = 3,
                patient_id = patients[0].id!!,
                diagnose_id = diagnoses[1].id,
                problem_category_id = problemCategories[1].id,
                medical_worker_id = medicalWorkers[2].id,
                created_at = dateC,
                visit_date = dateC
            ),
            MedicalRecordDataSourceModel(
                id = 4,
                patient_id = patients[0].id!!,
                diagnose_id = diagnoses[1].id,
                created_at = dateD,
                visit_date = dateD
            )
        )
    }
}
