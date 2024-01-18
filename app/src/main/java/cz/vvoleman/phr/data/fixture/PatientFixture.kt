package cz.vvoleman.phr.data.fixture

import cz.vvoleman.phr.common.data.datasource.model.PatientDao
import cz.vvoleman.phr.common.data.datasource.model.PatientDataSourceModel

class PatientFixture(private val patientDao: PatientDao) {

    suspend fun setup(): List<PatientDataSourceModel> {
        val patients = getData()

        patients.forEach {
            patientDao.insert(it)
        }

        return patients
    }

    @Suppress("MagicNumber")
    private fun getData(): List<PatientDataSourceModel> {
        return listOf(
            PatientDataSourceModel(
                1,
                "Výchozí",
            ),
        )
    }
}
