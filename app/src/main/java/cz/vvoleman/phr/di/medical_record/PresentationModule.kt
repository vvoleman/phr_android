package cz.vvoleman.phr.di.medical_record

import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.feature_medicalrecord.data.repository.MedicalRecordRepository
import cz.vvoleman.phr.feature_medicalrecord.domain.repository.GetSelectedPatientRepository
import cz.vvoleman.phr.feature_medicalrecord.domain.repository.MedicalRecordFilterRepository
import cz.vvoleman.phr.feature_medicalrecord.domain.usecase.GetFilteredRecordsUseCase
import cz.vvoleman.phr.feature_medicalrecord.domain.usecase.GetSelectedPatientUseCase
import cz.vvoleman.phr.feature_medicalrecord.presentation.addedit.mapper.PatientDomainModelToPresentationMapper
import cz.vvoleman.phr.feature_medicalrecord.presentation.list.mapper.ListViewStateToDomainMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class PresentationModule {

    @Provides
    fun providesGetFilteredRecordsUseCase(
        medicalRecordRepository: MedicalRecordFilterRepository,
        coroutineContextProvider: CoroutineContextProvider
    ) = GetFilteredRecordsUseCase(
        medicalRecordRepository,
        coroutineContextProvider
    )

    @Provides
    fun providesGetSelectedPatientUseCase(
        getSelectedPatientRepository: GetSelectedPatientRepository
    ) = GetSelectedPatientUseCase(
        getSelectedPatientRepository
    )

    @Provides
    fun providesPatientDomainToPresentationMapper() = PatientDomainModelToPresentationMapper()

    @Provides
    fun providesListViewStateToDomainMapper() = ListViewStateToDomainMapper()



}