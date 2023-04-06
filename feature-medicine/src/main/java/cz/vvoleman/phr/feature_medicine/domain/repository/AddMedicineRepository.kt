package cz.vvoleman.phr.feature_medicine.domain.repository

import cz.vvoleman.phr.feature_medicine.domain.model.medicine.MedicineDomainModel

interface AddMedicineRepository {

    suspend fun addMedicine(medicine: MedicineDomainModel)

}