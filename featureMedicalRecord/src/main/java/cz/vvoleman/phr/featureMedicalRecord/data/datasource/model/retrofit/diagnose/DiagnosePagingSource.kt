package cz.vvoleman.phr.featureMedicalRecord.data.datasource.model.retrofit.diagnose

import androidx.paging.PagingSource
import androidx.paging.PagingState
import cz.vvoleman.phr.featureMedicalRecord.data.datasource.model.retrofit.BackendApi
import cz.vvoleman.phr.featureMedicalRecord.data.mapper.DiagnoseApiModelToDbMapper
import cz.vvoleman.phr.featureMedicalRecord.data.mapper.DiagnoseDataSourceToDomainMapper
import cz.vvoleman.phr.featureMedicalRecord.domain.model.DiagnoseDomainModel

class DiagnosePagingSource(
    private val backendApi: BackendApi,
    private val query: String,
    private val diagnoseApiModelToDbMapper: DiagnoseApiModelToDbMapper,
    private val diagnoseDataSourceToDomainMapper: DiagnoseDataSourceToDomainMapper
) : PagingSource<Int, DiagnoseDomainModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DiagnoseDomainModel> {
        val position = params.key ?: 0
        return try {
            val response = backendApi.searchDiagnoses(query, position, params.loadSize)
            val items = response.data.map {
                val diagnose = diagnoseApiModelToDbMapper.toDb(it)
                diagnoseDataSourceToDomainMapper.toDomain(diagnose)
            }
            LoadResult.Page(
                data = items,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (items.isEmpty()) null else position + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, DiagnoseDomainModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    companion object {
        private const val STARTING_PAGE_INDEX = 1
    }
}
