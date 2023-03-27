package cz.vvoleman.phr.feature_medicalrecord.data.repository

import android.util.Log
import cz.vvoleman.phr.feature_medicalrecord.data.datasource.model.retrofit.BackendApi
import cz.vvoleman.phr.feature_medicalrecord.data.datasource.model.room.diagnose.DiagnoseDao
import cz.vvoleman.phr.feature_medicalrecord.data.mapper.DiagnoseApiModelToDbMapper
import cz.vvoleman.phr.feature_medicalrecord.data.mapper.DiagnoseDataSourceToDomainMapper
import cz.vvoleman.phr.feature_medicalrecord.domain.model.DiagnoseDomainModel
import cz.vvoleman.phr.feature_medicalrecord.domain.repository.select_file.GetDiagnosesByIdsRepository
import kotlinx.coroutines.flow.first

class DiagnoseRepository(
    private val diagnoseApiModelToDbMapper: DiagnoseApiModelToDbMapper,
    private val diagnoseDataSourceToDomainMapper: DiagnoseDataSourceToDomainMapper,
    private val backendApi: BackendApi,
    private val diagnoseDao: DiagnoseDao,
) : GetDiagnosesByIdsRepository {

    override suspend fun getDiagnosesByIds(ids: List<String>): List<DiagnoseDomainModel> {

        // Check if all diagnoses are in local databas
        val diagnoses = diagnoseDao.getByIds(ids).first()
        val allDiagnoses = diagnoses
            .map { diagnoseDataSourceToDomainMapper.toDomain(it.diagnose) }
            .toMutableList()


        // Check missing diagnoses
        val missingDiagnoses = diagnoses.map { it.diagnose.id }.toSet().let { ids.toSet() - it }

        // Get missing diagnoses from backend
        try {
            if (missingDiagnoses.isNotEmpty()) {
                backendApi.getDiagnosesByIds(missingDiagnoses).data.map {
                    diagnoseApiModelToDbMapper.toDb(it)
                }.let {
                    diagnoseDao.insert(it)
                    it.forEach { diagnose ->
                        allDiagnoses.add(diagnoseDataSourceToDomainMapper.toDomain(diagnose)) }
                }
            }
        } catch (e: Exception) {
            Log.e("DiagnoseRepository", "Error while getting diagnoses from backend", e)
        }

        // Get all diagnoses from database
        return allDiagnoses
    }
}