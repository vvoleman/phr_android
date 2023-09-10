package cz.vvoleman.phr.data.fixture

import cz.vvoleman.phr.common.data.datasource.model.PatientDataSourceModel
import cz.vvoleman.phr.featureMedicalRecord.data.datasource.model.room.category.ProblemCategoryDao
import cz.vvoleman.phr.featureMedicalRecord.data.datasource.model.room.category.ProblemCategoryDataSourceModel
import java.time.LocalDate

class ProblemCategoryFixture(
    private val problemCategoryDao: ProblemCategoryDao,
    private val patients: List<PatientDataSourceModel>
) {

    suspend fun setup(): List<ProblemCategoryDataSourceModel> {
        val problemCategories = getData()

        problemCategories.forEach {
            problemCategoryDao.insert(it)
        }

        return problemCategories
    }

    private fun getData(): List<ProblemCategoryDataSourceModel> {
        val problemCategories = mutableListOf<ProblemCategoryDataSourceModel>()

        patients.getOrNull(0)?.let {
            problemCategories.add(
                ProblemCategoryDataSourceModel(
                    id = 1,
                    name = "Nehoda 2022",
                    createdAt = LocalDate.now(),
                    color = "#3A6B83",
                    patientId = it.id!!
                )
            )
        }

        patients.getOrNull(1)?.let {
            problemCategories.add(
                ProblemCategoryDataSourceModel(
                    id = 1,
                    name = "Operace 2019",
                    createdAt = LocalDate.now(),
                    color = "#992624",
                    patientId = it.id!!
                )
            )
        }

        return problemCategories
    }
}
