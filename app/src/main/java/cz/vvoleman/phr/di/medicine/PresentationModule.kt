package cz.vvoleman.phr.di.medicine

import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.feature_medicine.domain.repository.SearchMedicineRepository
import cz.vvoleman.phr.feature_medicine.domain.repository.timeline.GetSchedulesByPatientRepository
import cz.vvoleman.phr.feature_medicine.domain.usecase.GetScheduledInTimeRangeUseCase
import cz.vvoleman.phr.feature_medicine.domain.usecase.SearchMedicineUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class PresentationModule {

    @Provides
    fun providesSearchMedicineUseCase(
        searchMedicineRepository: SearchMedicineRepository,
        coroutineContextProvider: CoroutineContextProvider
    ) = SearchMedicineUseCase(
        searchMedicineRepository,
        coroutineContextProvider
    )

    @Provides
    fun providesGetScheduledInTimeRangeUseCase(
        getSchedulesByPatientRepository: GetSchedulesByPatientRepository,
        coroutineContextProvider: CoroutineContextProvider
    ) = GetScheduledInTimeRangeUseCase(
        getSchedulesByPatientRepository,
        coroutineContextProvider
    )

}