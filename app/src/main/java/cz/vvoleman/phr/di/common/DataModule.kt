package cz.vvoleman.phr.di.common

import android.content.Context
import cz.vvoleman.phr.common.data.alarm.AlarmScheduler
import cz.vvoleman.phr.common.data.alarm.AndroidAlarmScheduler
import cz.vvoleman.phr.common.data.datasource.model.PatientDao
import cz.vvoleman.phr.common.data.datasource.model.PatientDataStore
import cz.vvoleman.phr.common.data.mapper.PatientDataSourceModelToAddEditMapper
import cz.vvoleman.phr.common.data.mapper.PatientDataSourceModelToDomainMapper
import cz.vvoleman.phr.common.data.repository.PatientRepository
import cz.vvoleman.phr.common.domain.repository.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    fun providesPatientRepository(
        patientDataStore: PatientDataStore,
        patientDomainModelToDataSourceMapper: PatientDataSourceModelToDomainMapper,
        patientDao: PatientDao,
        patientDataSourceModelToAddEditMapper: PatientDataSourceModelToAddEditMapper
    ) = PatientRepository(
        patientDao,
        patientDataStore,
        patientDomainModelToDataSourceMapper,
        patientDataSourceModelToAddEditMapper
    )

    @Provides
    fun providesSwitchSelectedPatientRepository(
        patientRepository: PatientRepository
    ): SwitchSelectedPatientRepository = patientRepository

    @Provides
    fun providesGetPatientByIdRepository(
        patientRepository: PatientRepository
    ): GetPatientByIdRepository = patientRepository

    @Provides
    fun providesGetAllPatientsRepository(
        patientRepository: PatientRepository
    ): GetAllPatientsRepository = patientRepository

    @Provides
    fun providesGetSelectedPatientRepository(
        patientRepository: PatientRepository
    ): GetSelectedPatientRepository = patientRepository

    @Provides
    fun providesSavePatientRepository(
        patientRepository: PatientRepository
    ): SavePatientRepository = patientRepository

    @Provides
    fun providesPatientDomainModelToDataSourceMapper() = PatientDataSourceModelToDomainMapper()

    @Provides
    fun providesPatientDataSourceModelToAddEditMapper() = PatientDataSourceModelToAddEditMapper()

    @Provides
    fun providesDeletePatientRepository(
        patientRepository: PatientRepository
    ): DeletePatientRepository = patientRepository

    @Provides
    fun providesAlarmScheduler(@ApplicationContext context: Context): AlarmScheduler =
        AndroidAlarmScheduler(context)
}
