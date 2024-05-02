package cz.vvoleman.phr.featureMedicine.data.datasource.retrofit.medicine

import androidx.paging.PagingSource
import androidx.paging.PagingState
import cz.vvoleman.phr.featureMedicine.data.datasource.retrofit.BackendApi
import cz.vvoleman.phr.featureMedicine.data.datasource.retrofit.medicine.mapper.MedicineApiDataSourceModelToDataMapper
import cz.vvoleman.phr.featureMedicine.data.mapper.medicine.MedicineDataModelToDomainMapper
import cz.vvoleman.phr.featureMedicine.domain.model.medicine.MedicineDomainModel

class MedicinePagingSource(
    private val backendApi: BackendApi,
    private val query: String,
    private val apiMapper: MedicineApiDataSourceModelToDataMapper,
    private val dataMapper: MedicineDataModelToDomainMapper,
) : PagingSource<Int, MedicineDomainModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MedicineDomainModel> {
        val position = params.key ?: 0

        return try {
            val response = backendApi.searchMedicine(query, position, params.loadSize)
            val items = response.data
                .map { apiMapper.toData(it) }
                .map { dataMapper.toDomain(it) }

            LoadResult.Page(
                data = items,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (items.isEmpty()) null else position + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MedicineDomainModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    companion object {
        private const val STARTING_PAGE_INDEX = 0
    }
}
