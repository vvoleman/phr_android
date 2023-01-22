package cz.vvoleman.phr.data.repository

import android.net.ConnectivityManager
import cz.vvoleman.phr.api.backend.BackendApi
import cz.vvoleman.phr.api.backend.DiagnoseResponse
import cz.vvoleman.phr.data.diagnose.DiagnoseDao
import cz.vvoleman.phr.data.diagnose.DiagnoseWithGroup
import cz.vvoleman.phr.util.network.NetworkConnectivityObserver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

@OptIn(FlowPreview::class)
@Singleton
class DiagnoseRepository @Inject constructor(
    private val backendApi: BackendApi,
    private val DiagnoseDao: DiagnoseDao,
    private val connectivityManager: ConnectivityManager
) {

    fun getDiagnoses(query: String): Flow<List<DiagnoseWithGroup>> {
        val localData = DiagnoseDao.getDiagnoseWithGroupByName(query)

        if (connectivityManager.activeNetwork == null) {
            return localData
        }

        val retrofitFlow: Flow<DiagnoseResponse> = flow {
            emit(backendApi.searchDiagnoses(query, 1))
        }.flowOn(Dispatchers.IO)

        val networkData = retrofitFlow
            .map { response ->
                // remap the response to a list of DiagnoseWithGroup
                response.data.map { diagnose ->
                    DiagnoseWithGroup(
                        diagnose.getEntity(),
                        diagnose.parent.getEntity()
                    )
                }
            }.catch {
                DiagnoseDao.getDiagnoseWithGroupByName(query)
            }

        return networkData
    }

}