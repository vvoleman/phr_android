package cz.vvoleman.phr.common.domain.repository.healthcare

import androidx.paging.PagingData
import cz.vvoleman.phr.common.domain.model.healthcare.facility.MedicalFacilityDomainModel
import kotlinx.coroutines.flow.Flow

interface GetFacilitiesPagingStreamRepository {

    fun getFacilitiesPagingStream(query: String): Flow<PagingData<MedicalFacilityDomainModel>>
}
