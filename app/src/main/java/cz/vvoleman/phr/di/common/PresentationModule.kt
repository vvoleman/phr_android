package cz.vvoleman.phr.di.common

import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.common.domain.repository.DeletePatientRepository
import cz.vvoleman.phr.common.domain.repository.GetAllPatientsRepository
import cz.vvoleman.phr.common.domain.repository.GetSelectedPatientRepository
import cz.vvoleman.phr.common.domain.repository.SwitchSelectedPatientRepository
import cz.vvoleman.phr.common.domain.usecase.DeletePatientUseCase
import cz.vvoleman.phr.common.domain.usecase.GetAllPatientsUseCase
import cz.vvoleman.phr.common.domain.usecase.GetSelectedPatientUseCase
import cz.vvoleman.phr.common.domain.usecase.SwitchSelectedPatientUseCase
import cz.vvoleman.phr.common.presentation.mapper.PatientPresentationModelToDomainMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class PresentationModule {

    @Provides
    fun providesGetSelectedPatientUseCase(
        getSelectedPatientRepository: GetSelectedPatientRepository
    ) = GetSelectedPatientUseCase(
        getSelectedPatientRepository
    )

    @Provides
    fun providesGetAllPatientsUseCase(
        getAllPatientsRepository: GetAllPatientsRepository,
        coroutineContextProvider: CoroutineContextProvider
    ) = GetAllPatientsUseCase(
        getAllPatientsRepository,
        coroutineContextProvider
    )

    @Provides
    fun providesSwitchSelectedPatientUseCase(
        switchSelectedPatientRepository: SwitchSelectedPatientRepository,
        coroutineContextProvider: CoroutineContextProvider
    ) = SwitchSelectedPatientUseCase(
        switchSelectedPatientRepository,
        coroutineContextProvider
    )

    @Provides
    fun providesDeletePatientUseCase(
        deletePatientRepository: DeletePatientRepository,
        getSelectedPatientRepository: GetSelectedPatientRepository,
        getAllPatientsRepository: GetAllPatientsRepository,
        switchSelectedPatientRepository: SwitchSelectedPatientRepository,
        coroutineContextProvider: CoroutineContextProvider
    ) = DeletePatientUseCase(
        deletePatientRepository,
        getSelectedPatientRepository,
        getAllPatientsRepository,
        switchSelectedPatientRepository,
        coroutineContextProvider
    )

    @Provides
    fun providesPatientPresentationModelToDomainMapper() = PatientPresentationModelToDomainMapper()

}