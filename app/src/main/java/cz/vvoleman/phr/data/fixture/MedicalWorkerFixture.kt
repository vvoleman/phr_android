package cz.vvoleman.phr.data.fixture

import cz.vvoleman.phr.common.data.datasource.model.AddressDataSourceModel
import cz.vvoleman.phr.feature_medicalrecord.data.datasource.model.room.worker.MedicalWorkerDao
import cz.vvoleman.phr.feature_medicalrecord.data.datasource.model.room.worker.MedicalWorkerDataSourceModel

class MedicalWorkerFixture(
    private val medicalWorkerDao: MedicalWorkerDao
) {

    suspend fun setup(): List<MedicalWorkerDataSourceModel> {
        val medicalWorkers = getData()

        medicalWorkers.forEach {
            medicalWorkerDao.insert(it)
        }

        return medicalWorkers
    }

    private fun getData(): List<MedicalWorkerDataSourceModel> {
        return listOf(
            MedicalWorkerDataSourceModel(
                id = 1,
                name = "MUDr. Jan Novák",
                email = "novak.j@seznam.cz",
                patientId = 1,
                address = AddressDataSourceModel(
                    city = "Ústí nad Labem",
                    street = "Kollárova",
                    house_number = "226/2",
                    zip_code = "40003"
                ),
                phone = "777 777 777"
            ),
            MedicalWorkerDataSourceModel(
                id = 2,
                name = "MUDr. Petr Dvořák",
                email = "dvorak.j@seznam.cz",
                patientId = 1,
                address = AddressDataSourceModel(
                    city = "Děčín",
                    street = "U Zámku",
                    house_number = "1",
                    zip_code = "41101"
                ),
                phone = "680 680 680"
            ),
            MedicalWorkerDataSourceModel(
                id = 3,
                name = "MUDr. Hana Novotná",
                email = "novotna.h@seznam.cz",
                patientId = 2,
                address = AddressDataSourceModel(
                    city = "Ústí nad Labem",
                    street = "Kollárova",
                    house_number = "226/2",
                    zip_code = "40003"
                ),
                phone = "777 777 777"
            )
        )
    }
}
