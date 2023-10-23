package cz.vvoleman.phr.featureMedicine.domain.repository

import cz.vvoleman.phr.featureMedicine.domain.model.medicine.MedicineDomainModel

interface GetMedicineByIdRepository {

    suspend fun getMedicineById(id: String): MedicineDomainModel?

}