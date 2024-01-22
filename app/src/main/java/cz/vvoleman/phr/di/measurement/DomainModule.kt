package cz.vvoleman.phr.di.measurement

import cz.vvoleman.featureMeasurement.domain.repository.SaveMeasurementGroupRepository
import cz.vvoleman.featureMeasurement.domain.usecase.addEdit.SaveMeasurementGroupUseCase
import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {

    @Provides
    fun providesSaveMeasurementGroupUseCase(
        saveMeasurementGroupRepository: SaveMeasurementGroupRepository,
        coroutineContextProvider: CoroutineContextProvider
    ) = SaveMeasurementGroupUseCase(saveMeasurementGroupRepository, coroutineContextProvider)

}
