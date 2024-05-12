package cz.vvoleman.phr.featureMedicine.domain.repository

import androidx.paging.PagingData
import cz.vvoleman.phr.featureMedicine.domain.model.medicine.MedicineDomainModel
import kotlinx.coroutines.flow.Flow

interface GetMedicinesPagingStreamRepository {

    fun getMedicinesPagingStream(query: String): Flow<PagingData<MedicineDomainModel>>
}
