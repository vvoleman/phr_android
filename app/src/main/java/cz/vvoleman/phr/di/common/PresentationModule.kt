package cz.vvoleman.phr.di.common

import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.common.domain.repository.*
import cz.vvoleman.phr.common.domain.repository.patient.DeletePatientRepository
import cz.vvoleman.phr.common.domain.repository.patient.GetAllPatientsRepository
import cz.vvoleman.phr.common.domain.repository.patient.GetPatientByIdRepository
import cz.vvoleman.phr.common.domain.repository.patient.GetSelectedPatientRepository
import cz.vvoleman.phr.common.domain.repository.patient.SavePatientRepository
import cz.vvoleman.phr.common.domain.repository.patient.SwitchSelectedPatientRepository
import cz.vvoleman.phr.common.domain.usecase.patient.DeletePatientUseCase
import cz.vvoleman.phr.common.domain.usecase.patient.GetAllPatientsUseCase
import cz.vvoleman.phr.common.domain.usecase.patient.GetSelectedPatientUseCase
import cz.vvoleman.phr.common.domain.usecase.patient.SwitchSelectedPatientUseCase
import cz.vvoleman.phr.common.domain.usecase.patient.addedit.GetPatientByIdUseCase
import cz.vvoleman.phr.common.domain.usecase.patient.addedit.SavePatientUseCase
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
    fun providesGetPatientByIdUseCase(
        getPatientByIdRepository: GetPatientByIdRepository,
        coroutineContextProvider: CoroutineContextProvider
    ) = GetPatientByIdUseCase(
        getPatientByIdRepository,
        coroutineContextProvider
    )

    @Provides
    fun providesSavePatientUseCase(
        savePatientRepository: SavePatientRepository,
        getPatientByIdRepository: GetPatientByIdRepository,
        coroutineContextProvider: CoroutineContextProvider
    ) = SavePatientUseCase(
        savePatientRepository,
        getPatientByIdRepository,
        coroutineContextProvider
    )

    @Provides
    fun providesPatientPresentationModelToDomainMapper() = PatientPresentationModelToDomainMapper()
}
