package cz.vvoleman.phr.featureMedicine.data.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import cz.vvoleman.phr.featureMedicine.data.datasource.retrofit.BackendApi
import cz.vvoleman.phr.featureMedicine.data.datasource.retrofit.medicine.MedicinePagingSource
import cz.vvoleman.phr.featureMedicine.data.datasource.retrofit.medicine.mapper.MedicineApiDataSourceModelToDataMapper
import cz.vvoleman.phr.featureMedicine.data.datasource.room.medicine.dao.MedicineDao
import cz.vvoleman.phr.featureMedicine.data.datasource.room.medicine.dao.ProductFormDao
import cz.vvoleman.phr.featureMedicine.data.datasource.room.medicine.dao.SubstanceDao
import cz.vvoleman.phr.featureMedicine.data.datasource.room.medicine.mapper.MedicineDataSourceModelToDataMapper
import cz.vvoleman.phr.featureMedicine.data.datasource.room.medicine.mapper.ProductFormDataSourceModelToDataMapper
import cz.vvoleman.phr.featureMedicine.data.datasource.room.medicine.mapper.SubstanceDataSourceModelToDataMapper
import cz.vvoleman.phr.featureMedicine.data.mapper.medicine.MedicineDataModelToDomainMapper
import cz.vvoleman.phr.featureMedicine.data.mapper.medicine.ProductFormDataModelToDomainMapper
import cz.vvoleman.phr.featureMedicine.data.mapper.medicine.SubstanceDataModelToDomainMapper
import cz.vvoleman.phr.featureMedicine.domain.model.medicine.MedicineDomainModel
import cz.vvoleman.phr.featureMedicine.domain.repository.AddMedicineRepository
import cz.vvoleman.phr.featureMedicine.domain.repository.GetMedicineByIdRepository
import cz.vvoleman.phr.featureMedicine.domain.repository.GetMedicinesPagingStreamRepository
import cz.vvoleman.phr.featureMedicine.domain.repository.SearchMedicineRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

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
) : SearchMedicineRepository, AddMedicineRepository, GetMedicineByIdRepository, GetMedicinesPagingStreamRepository {

    override suspend fun searchMedicine(query: String, page: Int): List<MedicineDomainModel> {
        return try {
            val response = backendApi.searchMedicine(query, page)

            Log.d(TAG, "searchMedicine: ${response.data.size}")

            response.data
                .map { medicineApiMapper.toData(it) }
                .map { medicineDataMapper.toDomain(it) }
                .onEach { medicine -> addMedicine(medicine) }
        } catch (e: Exception) {
            Log.e(TAG, "searchMedicine: ", e)
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
    }

    override suspend fun getMedicineById(id: String): MedicineDomainModel? {
        val localMedicine = medicineDao.getById(id).firstOrNull()

        if (localMedicine != null) {
            return medicineDataMapper.toDomain(medicineDataSourceMapper.toData(localMedicine))
        }

        Log.d(TAG, "Medicine \"$id\" not found in local database, searching in backend")

        val backendMedicine = retrieveMedicineFromBackend(id)

        if (backendMedicine != null) {
            addMedicine(backendMedicine)
            Log.d(TAG, "Medicine \"$id\" found in backend, adding to local database")
        }

        return backendMedicine
    }

    override fun getMedicinesPagingStream(query: String): Flow<PagingData<MedicineDomainModel>> {
        return Pager(
            config = PagingConfig(pageSize = PER_PAGE, enablePlaceholders = false),
            pagingSourceFactory = {
                MedicinePagingSource(
                    backendApi = backendApi,
                    query = query,
                    apiMapper = medicineApiMapper,
                    dataMapper = medicineDataMapper
                )
            }
        ).flow
    }

    companion object {
        const val TAG = "MedicineRepository"
        const val PER_PAGE = 10
    }

    private suspend fun retrieveMedicineFromBackend(id: String): MedicineDomainModel? {
        try {
            val response = backendApi.searchMedicine(id, 0)

            // Check if result is of size 1
            if (response.data.size == 1) {
                medicineApiMapper.toData(response.data.first()).let { medicine ->
                    return medicineDataMapper.toDomain(medicine)
                }
            }

            Log.d(TAG, "getMedicineById: duplicate medicine found for id \"$id\"")
        } catch (e: Exception) {
            Log.e(TAG, "getMedicineById: ", e)
        }

        return null
    }

}
