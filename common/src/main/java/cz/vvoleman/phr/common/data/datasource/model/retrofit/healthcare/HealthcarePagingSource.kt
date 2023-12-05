package cz.vvoleman.phr.common.data.datasource.model.retrofit.healthcare

import androidx.paging.PagingSource
import androidx.paging.PagingState
import cz.vvoleman.phr.common.data.mapper.healthcare.MedicalFacilityApiModelToDbMapper
import cz.vvoleman.phr.common.data.mapper.healthcare.MedicalFacilityDataSourceModelToDomainMapper
import cz.vvoleman.phr.common.domain.model.healthcare.facility.MedicalFacilityDomainModel

class HealthcarePagingSource(
    private val facilityMapper: MedicalFacilityDataSourceModelToDomainMapper,
    private val apiModelToDbMapper: MedicalFacilityApiModelToDbMapper,
    private val healthcareApi: HealthcareApi,
    private val query: String
) : PagingSource<Int, MedicalFacilityDomainModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MedicalFacilityDomainModel> {
        return try {
            val nextPageNumber = params.key ?: STARTING_PAGE // Initial page number
            val response = healthcareApi.getFacilities(nextPageNumber, query)

            val data = response.data.map { facilityMapper.toDomain(apiModelToDbMapper.toDb(it)) }

            LoadResult.Page(
                data = data,
                prevKey = if (nextPageNumber == STARTING_PAGE) null else nextPageNumber - 1,
                nextKey = if (data.isEmpty()) null else nextPageNumber + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MedicalFacilityDomainModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    companion object {
        const val STARTING_PAGE = 0
    }
}
