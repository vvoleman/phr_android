package cz.vvoleman.phr.featureMedicalRecord.domain.repository.addEdit

import androidx.paging.PagingData
import cz.vvoleman.phr.featureMedicalRecord.domain.model.DiagnoseDomainModel
import kotlinx.coroutines.flow.Flow

interface GetDiagnosesPagingStreamRepository {

    fun getDiagnosesPagingStream(query: String): Flow<PagingData<DiagnoseDomainModel>>

}
