package cz.vvoleman.phr.featureMedicalRecord.data.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import cz.vvoleman.phr.featureMedicalRecord.data.datasource.model.retrofit.BackendApi
import cz.vvoleman.phr.featureMedicalRecord.data.datasource.model.retrofit.diagnose.DiagnosePagingSource
import cz.vvoleman.phr.featureMedicalRecord.data.datasource.model.room.diagnose.DiagnoseDao
import cz.vvoleman.phr.featureMedicalRecord.data.mapper.DiagnoseApiModelToDbMapper
import cz.vvoleman.phr.featureMedicalRecord.data.mapper.DiagnoseDataSourceToDomainMapper
import cz.vvoleman.phr.featureMedicalRecord.domain.model.DiagnoseDomainModel
import cz.vvoleman.phr.featureMedicalRecord.domain.repository.GetDiagnoseByIdRepository
import cz.vvoleman.phr.featureMedicalRecord.domain.repository.addEdit.GetDiagnosesPagingStreamRepository
import cz.vvoleman.phr.featureMedicalRecord.domain.repository.addEdit.SearchDiagnoseRepository
import cz.vvoleman.phr.featureMedicalRecord.domain.repository.selectFile.GetDiagnosesByIdsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class DiagnoseRepository(
    private val diagnoseApiModelToDbMapper: DiagnoseApiModelToDbMapper,
    private val diagnoseDataSourceToDomainMapper: DiagnoseDataSourceToDomainMapper,
    private val backendApi: BackendApi,
    private val diagnoseDao: DiagnoseDao
) : GetDiagnosesByIdsRepository, GetDiagnoseByIdRepository, SearchDiagnoseRepository,
    GetDiagnosesPagingStreamRepository {

    override suspend fun getDiagnosesByIds(ids: List<String>): List<DiagnoseDomainModel> {
        // Check if all diagnoses are in local database
        val diagnoses = diagnoseDao.getByIds(ids).first()
        val allDiagnoses = diagnoses
            .map { diagnoseDataSourceToDomainMapper.toDomain(it) }
            .toMutableList()

        // Check missing diagnoses
        val missingDiagnoses = diagnoses.map { it.id }.toSet().let { ids.toSet() - it }

        // Get missing diagnoses from backend
        try {
            if (missingDiagnoses.isNotEmpty()) {
                addMissingDiagnoses(allDiagnoses, missingDiagnoses)
            }
        } catch (e: Exception) {
            Log.e("DiagnoseRepository", "Error while getting diagnoses from backend", e)
        }

        return allDiagnoses
    }

    private suspend fun addMissingDiagnoses(
        allDiagnoses: MutableList<DiagnoseDomainModel>,
        missingDiagnoses: Set<String>
    ) {
        Log.d(TAG, "Missing diagnoses: $missingDiagnoses")
        backendApi
            .getDiagnosesByIds(missingDiagnoses).data
            .map {
                diagnoseApiModelToDbMapper.toDb(it)
            }.let {
                Log.d(TAG, "Remote diagnoses found: $it")
                if (it.isEmpty()) return@let
                diagnoseDao.insert(it)
                it.forEach { diagnose ->
                    allDiagnoses.add(diagnoseDataSourceToDomainMapper.toDomain(diagnose))
                }
            }
    }

    override suspend fun getDiagnoseById(id: String): DiagnoseDomainModel? {
        return getDiagnosesByIds(listOf(id)).firstOrNull()
    }

    override suspend fun searchDiagnose(query: String, page: Int): List<DiagnoseDomainModel> {
        try {
            val response = backendApi.searchDiagnoses(query, page, PER_PAGE)

            val diagnoses = response.data.map { diagnoseApiModelToDbMapper.toDb(it) }
            diagnoseDao.insert(diagnoses)

            // Map the diagnoses to a list of DiagnoseDomainModel objects and return them
            return diagnoses.map { diagnoseDataSourceToDomainMapper.toDomain(it) }
        } catch (e: Exception) {
            // Log the error
            Log.e(TAG, "Error while searching diagnoses", e)
        }

        // If the remote search fails, fall back to the local storage
        return diagnoseDao.search(query).first()
            .map { diagnoseDataSourceToDomainMapper.toDomain(it.diagnose) }
    }

    override fun getDiagnosesPagingStream(query: String): Flow<PagingData<DiagnoseDomainModel>> {
        return Pager(
            config = PagingConfig(pageSize = PER_PAGE, enablePlaceholders = false),
            pagingSourceFactory = {
                DiagnosePagingSource(
                    backendApi = backendApi,
                    query = query,
                    diagnoseApiModelToDbMapper = diagnoseApiModelToDbMapper,
                    diagnoseDataSourceToDomainMapper = diagnoseDataSourceToDomainMapper

                )
            }
        ).flow
    }

    companion object {
        const val TAG = "DiagnoseRepository"
        const val PER_PAGE = 10
    }
}
