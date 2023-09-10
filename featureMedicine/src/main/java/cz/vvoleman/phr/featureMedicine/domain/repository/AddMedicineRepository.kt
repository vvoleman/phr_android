package cz.vvoleman.phr.featureMedicine.domain.repository

import cz.vvoleman.phr.featureMedicine.domain.model.medicine.MedicineDomainModel

interface AddMedicineRepository {

    suspend fun addMedicine(medicine: MedicineDomainModel)
}
