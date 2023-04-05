package cz.vvoleman.phr.feature_medicine.data.repository

import android.util.Log
import cz.vvoleman.phr.feature_medicine.data.datasource.retrofit.BackendApi
import cz.vvoleman.phr.feature_medicine.data.datasource.room.medicine.dao.MedicineDao
import cz.vvoleman.phr.feature_medicine.data.mapper.MedicineApiModelToDbMapper
import cz.vvoleman.phr.feature_medicine.data.mapper.MedicineDataSourceModelToDomainMapper
import cz.vvoleman.phr.feature_medicine.domain.model.medicine.MedicineDomainModel
import cz.vvoleman.phr.feature_medicine.domain.repository.SearchMedicineRepository

class MedicineRepository(
    private val backendApi: BackendApi,
    private val medicineApiModelToDbMapper: MedicineApiModelToDbMapper,
    private val medicineDataSourceModelToDomainMapper: MedicineDataSourceModelToDomainMapper,
    private val medicineDao: MedicineDao
) : SearchMedicineRepository {

    override suspend fun searchMedicine(query: String, page: Int): List<MedicineDomainModel> {
        return try {
            val response = backendApi.searchMedicine(query, page)

            Log.d("MedicineRepository", "searchMedicine: ${response.data.size}")

            response.data.map { medicineApiModelToDbMapper.toDb(it) }
                .map { medicineDataSourceModelToDomainMapper.toDomain(it) }
        } catch (e: Exception) {
            Log.e("MedicineRepository", "searchMedicine: ", e)
            emptyList()
        }
    }

}