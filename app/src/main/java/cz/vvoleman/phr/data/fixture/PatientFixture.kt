package cz.vvoleman.phr.data.fixture

import cz.vvoleman.phr.common.data.datasource.model.AddressDataSourceModel
import cz.vvoleman.phr.common.data.datasource.model.Gender
import cz.vvoleman.phr.common.data.datasource.model.PatientDao
import cz.vvoleman.phr.common.data.datasource.model.PatientDataSourceModel
import java.time.LocalDate

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
                "Vojta",
                AddressDataSourceModel(
                    city = "Ústí nad Labem",
                    street = "Kollárova",
                    houseNumber = "226/2",
                    zipCode = "40003"
                ),
                LocalDate.of(2001, 2, 7),
                Gender.MALE.name
            ),
            PatientDataSourceModel(
                2,
                "Petr",
                AddressDataSourceModel(
                    city = "Praha",
                    street = "Masarykova",
                    houseNumber = "22",
                    zipCode = "10000"
                ),
                LocalDate.of(2000, 1, 1),
                Gender.MALE.name
            )
        )
    }
}
