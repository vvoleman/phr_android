package cz.vvoleman.phr.featureMedicine.domain.repository

import cz.vvoleman.phr.featureMedicine.domain.model.medicine.MedicineDomainModel

interface SearchMedicineRepository {

    suspend fun searchMedicine(query: String, page: Int): List<MedicineDomainModel>
}
