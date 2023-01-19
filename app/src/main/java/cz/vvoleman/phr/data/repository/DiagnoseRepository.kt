package cz.vvoleman.phr.data.repository

import com.bumptech.glide.load.engine.Resource
import cz.vvoleman.phr.api.backend.BackendApi
import cz.vvoleman.phr.api.backend.DiagnoseResponse
import cz.vvoleman.phr.data.diagnose.DiagnoseDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.zip
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DiagnoseRepository @Inject constructor(
    private val backendApi: BackendApi,
    private val DiagnoseDao: DiagnoseDao
) {

    suspend fun getDiagnoses(query: String) : DiagnoseResponse {
        // Create a Flow
        // First, we add data from Room to the Flow
        // Then, we add data from API to the Flow
        // Finally, we return the Flow

        val localData = DiagnoseDao.getDiagnosesByName(query)
        val networkData = backendApi.searchDiagnoses(query, 1)

        return networkData
    }

}