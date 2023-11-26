package cz.vvoleman.phr.di.common

import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.common.domain.eventBus.CommonEventBus
import cz.vvoleman.phr.common.domain.eventBus.CommonListener
import cz.vvoleman.phr.common.domain.mapper.PatientDomainModelToAddEditMapper
import cz.vvoleman.phr.common.domain.repository.healthcare.GetMedicalWorkersWithServicesRepository
import cz.vvoleman.phr.common.domain.usecase.healthcare.GetMedicalWorkersUseCase
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
}
