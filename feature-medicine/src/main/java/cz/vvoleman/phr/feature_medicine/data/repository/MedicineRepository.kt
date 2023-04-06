package cz.vvoleman.phr.feature_medicine.data.repository

import android.util.Log
import cz.vvoleman.phr.feature_medicine.data.datasource.retrofit.BackendApi
import cz.vvoleman.phr.feature_medicine.data.datasource.retrofit.medicine.mapper.MedicineApiDataSourceModelToDataMapper
import cz.vvoleman.phr.feature_medicine.data.datasource.room.medicine.ProductFormDataSourceModel
import cz.vvoleman.phr.feature_medicine.data.datasource.room.medicine.SubstanceDataSourceModel
import cz.vvoleman.phr.feature_medicine.data.datasource.room.medicine.dao.*
import cz.vvoleman.phr.feature_medicine.data.datasource.room.medicine.mapper.MedicineDataSourceModelToDataMapper
import cz.vvoleman.phr.feature_medicine.data.datasource.room.medicine.mapper.ProductFormDataSourceModelToDataMapper
import cz.vvoleman.phr.feature_medicine.data.datasource.room.medicine.mapper.SubstanceDataSourceModelToDataMapper
import cz.vvoleman.phr.feature_medicine.data.mapper.*
import cz.vvoleman.phr.feature_medicine.domain.model.medicine.MedicineDomainModel
import cz.vvoleman.phr.feature_medicine.domain.repository.AddMedicineRepository
import cz.vvoleman.phr.feature_medicine.domain.repository.SearchMedicineRepository

class MedicineRepository(
    private val backendApi: BackendApi,
    private val medicineDao: MedicineDao,
    private val substanceDao: SubstanceDao,
    private val productFormDao: ProductFormDao,
    private val medicineApiMapper: MedicineApiDataSourceModelToDataMapper,
    private val medicineDataMapper: MedicineDataModelToDomainMapper,
    private val medicineDataSourceMapper: MedicineDataSourceModelToDataMapper,
    private val substanceDataMapper: SubstanceDataModelToDomainMapper,
    private val substanceDataSourceMapper: SubstanceDataSourceModelToDataMapper,
    private val productFormDataMapper: ProductFormDataModelToDomainMapper,
    private val productFormDataSourceMapper: ProductFormDataSourceModelToDataMapper
) : SearchMedicineRepository, AddMedicineRepository {

    override suspend fun searchMedicine(query: String, page: Int): List<MedicineDomainModel> {
        return try {
            val response = backendApi.searchMedicine(query, page)

            Log.d("MedicineRepository", "searchMedicine: ${response.data.size}")

            response.data
                .map { medicineApiMapper.toData(it) }
                .map { medicineDataMapper.toDomain(it) }
                .onEach { medicine -> addMedicine(medicine) }

        } catch (e: Exception) {
            Log.e("MedicineRepository", "searchMedicine: ", e)
            emptyList()
        }
    }

    override suspend fun addMedicine(medicine: MedicineDomainModel) {
        val model = medicineDataSourceMapper.toDataSource(medicineDataMapper.toData(medicine))

        val substances = medicine.substances
            .map { substanceDataMapper.toData(it.substance) }
            .map { substanceDataSourceMapper.toDataSource(it) }
        substanceDao.insert(substances)

        val productForm = medicine.packaging.form
            .let { productFormDataMapper.toData(it) }
            .let { productFormDataSourceMapper.toDataSource(it) }
        productFormDao.insert(productForm)

        medicineDao.insert(model)
        Log.d("MedicineRepository", "addMedicine: ${model}")
    }

}