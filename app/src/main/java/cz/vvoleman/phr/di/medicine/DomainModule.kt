package cz.vvoleman.phr.di.medicine

import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.featureMedicine.domain.repository.SaveMedicineScheduleRepository
import cz.vvoleman.phr.featureMedicine.domain.usecase.SaveMedicineScheduleUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {

    @Provides
    fun providesSaveMedicineScheduleUseCase(
        medicineScheduleRepository: SaveMedicineScheduleRepository,
        coroutineContextProvider: CoroutineContextProvider
    ) = SaveMedicineScheduleUseCase(
        medicineScheduleRepository,
        coroutineContextProvider
    )

}
