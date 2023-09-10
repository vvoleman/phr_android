package cz.vvoleman.phr.data.fixture

import cz.vvoleman.phr.common.data.datasource.model.PatientDataSourceModel
import cz.vvoleman.phr.featureMedicalRecord.data.datasource.model.room.MedicalRecordDao
import cz.vvoleman.phr.featureMedicalRecord.data.datasource.model.room.MedicalRecordDataSourceModel
import cz.vvoleman.phr.featureMedicalRecord.data.datasource.model.room.category.ProblemCategoryDataSourceModel
import cz.vvoleman.phr.featureMedicalRecord.data.datasource.model.room.diagnose.DiagnoseDataSourceModel
import cz.vvoleman.phr.featureMedicalRecord.data.datasource.model.room.worker.MedicalWorkerDataSourceModel
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
                patientId = patients[0].id!!,
                diagnoseId = diagnoses[0].id,
                problemCategoryId = problemCategories[0].id,
                medicalWorkerId = medicalWorkers[0].id,
                createdAt = dateA,
                visitDate = dateA
            ),
            MedicalRecordDataSourceModel(
                id = 2,
                patientId = patients[0].id!!,
                diagnoseId = diagnoses[1].id,
                problemCategoryId = problemCategories[0].id,
                medicalWorkerId = medicalWorkers[1].id,
                createdAt = dateB,
                visitDate = dateB
            ),
            MedicalRecordDataSourceModel(
                id = 3,
                patientId = patients[0].id!!,
                diagnoseId = diagnoses[1].id,
                problemCategoryId = problemCategories[1].id,
                medicalWorkerId = medicalWorkers[2].id,
                createdAt = dateC,
                visitDate = dateC
            ),
            MedicalRecordDataSourceModel(
                id = 4,
                patientId = patients[0].id!!,
                diagnoseId = diagnoses[1].id,
                createdAt = dateD,
                visitDate = dateD
            )
        )
    }
}
