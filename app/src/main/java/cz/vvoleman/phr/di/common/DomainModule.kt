package cz.vvoleman.phr.di.common

import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.common.domain.eventBus.CommonEventBus
import cz.vvoleman.phr.common.domain.eventBus.CommonListener
import cz.vvoleman.phr.common.domain.mapper.PatientDomainModelToAddEditMapper
import cz.vvoleman.phr.common.domain.repository.healthcare.GetMedicalWorkersWithServicesRepository
import cz.vvoleman.phr.common.domain.repository.healthcare.GetSpecificMedicalWorkersRepository
import cz.vvoleman.phr.common.domain.repository.healthcare.RemoveSpecificMedicalWorkerRepository
import cz.vvoleman.phr.common.domain.repository.healthcare.SaveMedicalFacilityRepository
import cz.vvoleman.phr.common.domain.repository.healthcare.SaveMedicalWorkerRepository
import cz.vvoleman.phr.common.domain.repository.healthcare.SaveSpecificMedicalWorkerRepository
import cz.vvoleman.phr.common.domain.usecase.healthcare.GetMedicalWorkersUseCase
import cz.vvoleman.phr.common.domain.usecase.healthcare.SaveMedicalWorkerUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.greenrobot.eventbus.EventBus

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {

    @Provides
    fun providesPatientDomainModelToAddEditMapper() = PatientDomainModelToAddEditMapper()

    @Provides
    fun providesCommonListener() = CommonListener()

    @Provides
    fun providesEventBus() = EventBus.getDefault()

    @Provides
    fun providesGetMedicalWorkersUseCase(
        commonEventBus: CommonEventBus,
        getMedicalWorkersWithServicesRepository: GetMedicalWorkersWithServicesRepository,
        coroutineContextProvider: CoroutineContextProvider
    ) = GetMedicalWorkersUseCase(
        commonEventBus,
        getMedicalWorkersWithServicesRepository,
        coroutineContextProvider
    )

    @Provides
    fun providesCommonEventBus() = CommonEventBus

    @Provides
    fun providesSaveMedicalWorkerUseCase(
        saveMedicalFacilityRepository: SaveMedicalFacilityRepository,
        saveMedicalWorkerRepository: SaveMedicalWorkerRepository,
        getSpecificMedicalWorkersRepository: GetSpecificMedicalWorkersRepository,
        saveSpecificMedicalWorkerRepository: SaveSpecificMedicalWorkerRepository,
        removeSpecificMedicalWorkerRepository: RemoveSpecificMedicalWorkerRepository,
        coroutineContextProvider: CoroutineContextProvider
    ) = SaveMedicalWorkerUseCase(
        saveMedicalFacilityRepository,
        saveMedicalWorkerRepository,
        getSpecificMedicalWorkersRepository,
        saveSpecificMedicalWorkerRepository,
        removeSpecificMedicalWorkerRepository,
        coroutineContextProvider
    )
}
