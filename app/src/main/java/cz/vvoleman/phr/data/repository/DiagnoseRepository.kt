package cz.vvoleman.phr.data.repository

import android.net.ConnectivityManager
import androidx.paging.Pager
import androidx.paging.PagingConfig
import cz.vvoleman.phr.api.backend.BackendApi
import cz.vvoleman.phr.api.backend.DiagnoseResponse
import cz.vvoleman.phr.data.core.DiagnoseGroup
import cz.vvoleman.phr.data.core.DiagnoseWithGroup
import cz.vvoleman.phr.data.diagnose.DiagnosePagingSource
import cz.vvoleman.phr.data.room.diagnose.*
import cz.vvoleman.phr.data.room.diagnose.DiagnoseWithGroup as RoomDiagnoseWithGroup
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
    private val diagnoseDao: DiagnoseDao,
    private val diagnoseGroupDao: DiagnoseGroupDao,
    private val connectivityManager: ConnectivityManager
) {

    fun getDiagnoses(query: String) =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = { DiagnosePagingSource(backendApi, query) }
        )

    suspend fun getDiagnoseById(id: String): RoomDiagnoseWithGroup? {
        // Try local, if not found, try remote
        val result = diagnoseDao.getById(id).firstOrNull()

        if (result != null) return result

        return backendApi.searchDiagnoses(id, 0, 1).data.firstOrNull()?.let {
            val dataDiagnose = it.toDiagnose()
            val dataGroup = it.parent.toDiagnoseGroup()

            RoomDiagnoseWithGroup(
                DiagnoseEntity.from(dataDiagnose, dataGroup),
                DiagnoseGroupEntity.from(dataGroup)
            )
        }
    }

    suspend fun createGroup(group: DiagnoseGroup, createDiagnoses: Boolean = true) {
        diagnoseGroupDao.insert(DiagnoseGroupEntity.from(group))

        if (!createDiagnoses) return

        val diagnoses = mutableListOf<DiagnoseEntity>()
        for (diagnose in group.diagnoses) {
            diagnoses.add(DiagnoseEntity.from(diagnose, group))
        }

        diagnoseDao.insert(diagnoses)
    }

    suspend fun create(diagnoseWithGroup: DiagnoseWithGroup) {
        diagnoseGroupDao.insert(DiagnoseGroupEntity.from(diagnoseWithGroup.diagnoseGroup))
        diagnoseDao.insert(
            DiagnoseEntity.from(
                diagnoseWithGroup.diagnose,
                diagnoseWithGroup.diagnoseGroup
            )
        )
    }

}