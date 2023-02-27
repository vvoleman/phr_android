package cz.vvoleman.phr.data.diagnose

import androidx.paging.PagingSource
import androidx.paging.PagingState
import cz.vvoleman.phr.api.backend.BackendApi
import cz.vvoleman.phr.data.room.diagnose.DiagnoseEntity
import cz.vvoleman.phr.data.room.diagnose.DiagnoseGroupEntity
import cz.vvoleman.phr.data.room.diagnose.DiagnoseWithGroup

private const val STARTING_PAGE_INDEX = 1

class DiagnosePagingSource(
    private val backendApi: BackendApi,
    private val query: String
) : PagingSource<Int, DiagnoseWithGroup>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DiagnoseWithGroup> {
        val position = params.key ?: 0
        return try {
            val response = backendApi.searchDiagnoses(query, position, params.loadSize)
            val items = response.data.map {
                val group = it.parent.toDiagnoseGroup()
                DiagnoseWithGroup(
                    DiagnoseEntity.from(it.toDiagnose(), group),
                    DiagnoseGroupEntity.from(group)
                )
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

    override fun getRefreshKey(state: PagingState<Int, DiagnoseWithGroup>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}