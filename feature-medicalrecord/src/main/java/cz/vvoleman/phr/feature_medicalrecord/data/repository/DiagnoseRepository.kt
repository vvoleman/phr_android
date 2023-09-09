package cz.vvoleman.phr.feature_medicalrecord.data.repository

import android.util.Log
import cz.vvoleman.phr.feature_medicalrecord.data.datasource.model.retrofit.BackendApi
import cz.vvoleman.phr.feature_medicalrecord.data.datasource.model.room.diagnose.DiagnoseDao
import cz.vvoleman.phr.feature_medicalrecord.data.mapper.DiagnoseApiModelToDbMapper
import cz.vvoleman.phr.feature_medicalrecord.data.mapper.DiagnoseDataSourceToDomainMapper
import cz.vvoleman.phr.feature_medicalrecord.domain.model.DiagnoseDomainModel
import cz.vvoleman.phr.feature_medicalrecord.domain.repository.GetDiagnoseByIdRepository
import cz.vvoleman.phr.feature_medicalrecord.domain.repository.add_edit.SearchDiagnoseRepository
import cz.vvoleman.phr.feature_medicalrecord.domain.repository.select_file.GetDiagnosesByIdsRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class DiagnoseRepository(
    private val diagnoseApiModelToDbMapper: DiagnoseApiModelToDbMapper,
    private val diagnoseDataSourceToDomainMapper: DiagnoseDataSourceToDomainMapper,
    private val backendApi: BackendApi,
    private val diagnoseDao: DiagnoseDao
) : GetDiagnosesByIdsRepository, GetDiagnoseByIdRepository, SearchDiagnoseRepository {

    override suspend fun getDiagnosesByIds(ids: List<String>): List<DiagnoseDomainModel> {
        // Check if all diagnoses are in local databas
        val diagnoses = diagnoseDao.getByIds(ids).first()
        val allDiagnoses = diagnoses
            .map { diagnoseDataSourceToDomainMapper.toDomain(it) }
            .toMutableList()

        // Check missing diagnoses
        val missingDiagnoses = diagnoses.map { it.id }.toSet().let { ids.toSet() - it }

        // Get missing diagnoses from backend
        try {
            if (missingDiagnoses.isNotEmpty()) {
                Log.d("DiagnoseRepository", "Missing diagnoses: $missingDiagnoses")
                backendApi.getDiagnosesByIds(missingDiagnoses).data.map {
                    diagnoseApiModelToDbMapper.toDb(it)
                }.let {
                    Log.d("DiagnoseRepository", "Remote diagnoses found: $it")
                    if (it.isEmpty()) return@let
                    diagnoseDao.insert(it)
                    it.forEach { diagnose ->
                        allDiagnoses.add(diagnoseDataSourceToDomainMapper.toDomain(diagnose))
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("DiagnoseRepository", "Error while getting diagnoses from backend", e)
        }

        // Get all diagnoses from database
        return allDiagnoses
    }

    override suspend fun getDiagnoseById(id: String): DiagnoseDomainModel? {
        return getDiagnosesByIds(listOf(id)).firstOrNull()
    }

    override suspend fun searchDiagnose(query: String, page: Int): List<DiagnoseDomainModel> {
        try {
            val response = backendApi.searchDiagnoses(query, page, 10)

            val diagnoses = response.data.map { diagnoseApiModelToDbMapper.toDb(it) }
            diagnoseDao.insert(diagnoses)

            // Map the diagnoses to a list of DiagnoseDomainModel objects and return them
            return diagnoses.map { diagnoseDataSourceToDomainMapper.toDomain(it) }
        } catch (e: Exception) {
            // Log the error
            Log.e("DiagnoseRepository", "Error while searching diagnoses", e)
        }

        // If the remote search fails, fall back to the local storage
        return diagnoseDao.search(query).first().map { diagnoseDataSourceToDomainMapper.toDomain(it.diagnose) }
    }
}
