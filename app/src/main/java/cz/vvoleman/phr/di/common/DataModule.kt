package cz.vvoleman.phr.di.common

import android.content.Context
import cz.vvoleman.phr.common.data.alarm.AlarmScheduler
import cz.vvoleman.phr.common.data.alarm.AndroidAlarmScheduler
import cz.vvoleman.phr.common.data.datasource.model.PatientDao
import cz.vvoleman.phr.common.data.datasource.model.PatientDataStore
import cz.vvoleman.phr.common.data.datasource.model.healthcare.facility.MedicalFacilityDao
import cz.vvoleman.phr.common.data.datasource.model.healthcare.service.MedicalServiceDao
import cz.vvoleman.phr.common.data.datasource.model.healthcare.worker.MedicalWorkerDao
import cz.vvoleman.phr.common.data.datasource.model.healthcare.worker.SpecificMedicalWorkerDao
import cz.vvoleman.phr.common.data.datasource.model.retrofit.healthcare.HealthcareApi
import cz.vvoleman.phr.common.data.mapper.PatientDataSourceModelToAddEditMapper
import cz.vvoleman.phr.common.data.mapper.PatientDataSourceModelToDomainMapper
import cz.vvoleman.phr.common.data.mapper.healthcare.MedicalFacilityApiModelToDbMapper
import cz.vvoleman.phr.common.data.mapper.healthcare.MedicalFacilityDataSourceModelToDomainMapper
import cz.vvoleman.phr.common.data.mapper.healthcare.MedicalServiceDataSourceModelToDomainMapper
import cz.vvoleman.phr.common.data.mapper.healthcare.MedicalServiceWithInfoDataSourceModelToDomainMapper
import cz.vvoleman.phr.common.data.mapper.healthcare.MedicalServiceWithWorkersDataSourceModelToDomainMapper
import cz.vvoleman.phr.common.data.mapper.healthcare.MedicalWorkerDataSourceModelToDomainMapper
import cz.vvoleman.phr.common.data.mapper.healthcare.MedicalWorkerWithInfoDataSourceModelToDomainMapper
import cz.vvoleman.phr.common.data.mapper.healthcare.MedicalWorkerWithServicesDataSourceModelToDomainMapper
import cz.vvoleman.phr.common.data.mapper.healthcare.SpecificMedicalWorkerDataSourceToDomainMapper
import cz.vvoleman.phr.common.data.repository.HealthcareRepository
import cz.vvoleman.phr.common.data.repository.PatientRepository
import cz.vvoleman.phr.common.data.repository.healthcare.FacilityRepository
import cz.vvoleman.phr.common.data.repository.healthcare.SpecificMedicalWorkerRepository
import cz.vvoleman.phr.common.domain.repository.*
import cz.vvoleman.phr.common.domain.repository.healthcare.DeleteMedicalWorkerRepository
import cz.vvoleman.phr.common.domain.repository.healthcare.GetFacilitiesByPatientRepository
import cz.vvoleman.phr.common.domain.repository.healthcare.GetFacilitiesPagingStreamRepository
import cz.vvoleman.phr.common.domain.repository.healthcare.GetFacilityByIdRepository
import cz.vvoleman.phr.common.domain.repository.healthcare.GetMedicalWorkersWithServicesRepository
import cz.vvoleman.phr.common.domain.repository.healthcare.GetSpecificMedicalWorkersRepository
import cz.vvoleman.phr.common.domain.repository.healthcare.RemoveSpecificMedicalWorkerRepository
import cz.vvoleman.phr.common.domain.repository.healthcare.SaveMedicalFacilityRepository
import cz.vvoleman.phr.common.domain.repository.healthcare.SaveMedicalWorkerRepository
import cz.vvoleman.phr.common.domain.repository.healthcare.SaveSpecificMedicalWorkerRepository
import cz.vvoleman.phr.common.domain.repository.patient.DeletePatientRepository
import cz.vvoleman.phr.common.domain.repository.patient.GetAllPatientsRepository
import cz.vvoleman.phr.common.domain.repository.patient.GetPatientByBirthDateRepository
import cz.vvoleman.phr.common.domain.repository.patient.GetPatientByIdRepository
import cz.vvoleman.phr.common.domain.repository.patient.GetSelectedPatientRepository
import cz.vvoleman.phr.common.domain.repository.patient.SavePatientRepository
import cz.vvoleman.phr.common.domain.repository.patient.SwitchSelectedPatientRepository
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
    fun providesGetPatientByBirthDateRepository(
        patientRepository: PatientRepository
    ): GetPatientByBirthDateRepository = patientRepository

    @Provides
    fun providesAlarmScheduler(@ApplicationContext context: Context): AlarmScheduler =
        AndroidAlarmScheduler(context)

    @Provides
    fun providesMedicalServiceDataSourceModelToDomainMapper() = MedicalServiceDataSourceModelToDomainMapper()

    @Provides
    fun providesMedicalWorkerDataSourceModelToDomainMapper() = MedicalWorkerDataSourceModelToDomainMapper()

    @Provides
    fun providesMedicalServiceWithInfoDataSourceModelToDomainMapper(
        medicalServiceDataSourceModelToDomainMapper: MedicalServiceDataSourceModelToDomainMapper,
    ) = MedicalServiceWithInfoDataSourceModelToDomainMapper(
        medicalServiceDataSourceModelToDomainMapper,
    )

    @Provides
    fun providesMedicalWorkerWithInfoDataSourceModelToDomainMapper(
        medicalWorkerDataSourceModelToDomainMapper: MedicalWorkerDataSourceModelToDomainMapper,
    ) = MedicalWorkerWithInfoDataSourceModelToDomainMapper(
        medicalWorkerDataSourceModelToDomainMapper,
    )

    @Provides
    fun providesMedicalServiceWithWorkersDataSourceModelToDomainMapper(
        specificWorkerDao: SpecificMedicalWorkerDao,
        medicalServiceDataSourceModelToDomainMapper: MedicalServiceDataSourceModelToDomainMapper,
        workerMapper: MedicalWorkerDataSourceModelToDomainMapper,
        medicalWorkerDataSourceModelToDomainMapper: MedicalWorkerWithInfoDataSourceModelToDomainMapper,
    ) = MedicalServiceWithWorkersDataSourceModelToDomainMapper(
        specificWorkerDao,
        medicalServiceDataSourceModelToDomainMapper,
        workerMapper,
        medicalWorkerDataSourceModelToDomainMapper,
    )

    @Provides
    fun providesMedicalWorkerWithServicesDataSourceModelToDomainMapper(
        specificWorkerDao: SpecificMedicalWorkerDao,
        medicalWorkerDataSourceModelToDomainMapper: MedicalWorkerDataSourceModelToDomainMapper,
        medicalServiceDataSourceModelToDomainMapper: MedicalServiceWithInfoDataSourceModelToDomainMapper,
    ) = MedicalWorkerWithServicesDataSourceModelToDomainMapper(
        specificWorkerDao,
        medicalWorkerDataSourceModelToDomainMapper,
        medicalServiceDataSourceModelToDomainMapper,
    )

    @Provides
    fun providesSpecificMedicalWorkerDataSourceModelToDomainMapper(
        medicalWorkerDataSourceModelToDomainMapper: MedicalWorkerDataSourceModelToDomainMapper,
        medicalServiceDataSourceModelToDomainMapper: MedicalServiceDataSourceModelToDomainMapper,
    ) = SpecificMedicalWorkerDataSourceToDomainMapper(
        medicalWorkerDataSourceModelToDomainMapper,
        medicalServiceDataSourceModelToDomainMapper,
    )

    @Provides
    fun providesMedicalFacilityDataSourceModelToDomainMapper(
        serviceMapper: MedicalServiceDataSourceModelToDomainMapper,
        serviceWithWorkersMapper: MedicalServiceWithWorkersDataSourceModelToDomainMapper,
    ) = MedicalFacilityDataSourceModelToDomainMapper(
        serviceMapper,
        serviceWithWorkersMapper,
    )

    @Provides
    fun providesMedicalFacilityApiModelToDbMapper() = MedicalFacilityApiModelToDbMapper()

    @Provides
    fun providesHealthcareRepository(
        medicalWorkerDao: MedicalWorkerDao,
        medicalWorkerWithServicesMapper: MedicalWorkerWithServicesDataSourceModelToDomainMapper,
        medicalWorkerMapper: MedicalWorkerDataSourceModelToDomainMapper,
    ) = HealthcareRepository(
        medicalWorkerDao = medicalWorkerDao,
        medicalWorkerWithServicesMapper = medicalWorkerWithServicesMapper,
        medicalWorkerMapper = medicalWorkerMapper,
    )

    @Provides
    fun providesFacilityRepository(
        facilityMapper: MedicalFacilityDataSourceModelToDomainMapper,
        api: HealthcareApi,
        apiModelToDbMapper: MedicalFacilityApiModelToDbMapper,
        medicalFacilityDao: MedicalFacilityDao,
        medicalServiceDao: MedicalServiceDao,
    ) = FacilityRepository(
        facilityMapper = facilityMapper,
        api = api,
        apiModelToDbMapper = apiModelToDbMapper,
        medicalFacilityDao = medicalFacilityDao,
        medicalServiceDao = medicalServiceDao,
    )

    @Provides
    fun providesGetMedicalWorkersWithServicesRepository(
        healthcareRepository: HealthcareRepository
    ): GetMedicalWorkersWithServicesRepository = healthcareRepository

    @Provides
    fun providesGetFacilitiesPagingStreamRepository(
        facilityRepository: FacilityRepository
    ): GetFacilitiesPagingStreamRepository = facilityRepository

    @Provides
    fun providesSaveMedicalFacilityRepository(
        facilityRepository: FacilityRepository
    ): SaveMedicalFacilityRepository = facilityRepository

    @Provides
    fun providesSaveMedicalWorkerRepository(
        healthcareRepository: HealthcareRepository
    ): SaveMedicalWorkerRepository = healthcareRepository

    @Provides
    fun providesGetFacilityByIdRepository(
        facilityRepository: FacilityRepository
    ): GetFacilityByIdRepository = facilityRepository

    @Provides
    fun providesSpecificMedicalWorkerRepository(
        medicalWorkerDao: MedicalWorkerDao,
        specificMedicalWorkerDao: SpecificMedicalWorkerDao,
        specificMapper: SpecificMedicalWorkerDataSourceToDomainMapper,
    ) = SpecificMedicalWorkerRepository(
        medicalWorkerDao,
        specificMedicalWorkerDao,
        specificMapper,
    )

    @Provides
    fun providesGetSpecificMedicalWorkerRepository(
        specificMedicalWorkerRepository: SpecificMedicalWorkerRepository
    ): GetSpecificMedicalWorkersRepository = specificMedicalWorkerRepository

    @Provides
    fun providesRemoveSpecificMedicalWorkerRepository(
        specificMedicalWorkerRepository: SpecificMedicalWorkerRepository
    ): RemoveSpecificMedicalWorkerRepository = specificMedicalWorkerRepository

    @Provides
    fun providesSaveSpecificMedicalWorkerRepository(
        specificMedicalWorkerRepository: SpecificMedicalWorkerRepository
    ): SaveSpecificMedicalWorkerRepository = specificMedicalWorkerRepository

    @Provides
    fun providesDeleteMedicalWorkerRepository(
        specificMedicalWorkerRepository: SpecificMedicalWorkerRepository
    ): DeleteMedicalWorkerRepository = specificMedicalWorkerRepository

    @Provides
    fun providesGetFacilitiesByPatientRepository(
        facilityRepository: FacilityRepository
    ): GetFacilitiesByPatientRepository = facilityRepository
}
