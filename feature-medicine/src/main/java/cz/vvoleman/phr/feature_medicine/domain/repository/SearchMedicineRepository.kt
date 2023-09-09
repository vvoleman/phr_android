package cz.vvoleman.phr.feature_medicine.domain.repository

import cz.vvoleman.phr.feature_medicine.domain.model.medicine.MedicineDomainModel

interface SearchMedicineRepository {

    suspend fun searchMedicine(query: String, page: Int): List<MedicineDomainModel>
}
