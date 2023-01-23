package cz.vvoleman.phr.data.repository

import android.net.ConnectivityManager
import androidx.paging.Pager
import androidx.paging.PagingConfig
import cz.vvoleman.phr.api.backend.BackendApi
import cz.vvoleman.phr.api.backend.DiagnoseResponse
import cz.vvoleman.phr.data.diagnose.DiagnoseDao
import cz.vvoleman.phr.data.diagnose.DiagnosePagingSource
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

    fun getDiagnoses(query: String) =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = {DiagnosePagingSource(backendApi, query)}
        )

}