package cz.vvoleman.phr.di.medical_record

import cz.vvoleman.phr.common.data.datasource.model.PatientDao
import cz.vvoleman.phr.common.data.datasource.model.PatientDataStore
import cz.vvoleman.phr.feature_medicalrecord.data.datasource.model.retrofit.BackendApi
import cz.vvoleman.phr.feature_medicalrecord.data.datasource.model.room.MedicalRecordDao
import cz.vvoleman.phr.feature_medicalrecord.data.datasource.model.room.category.ProblemCategoryDao
import cz.vvoleman.phr.feature_medicalrecord.data.datasource.model.room.diagnose.DiagnoseDao
import cz.vvoleman.phr.feature_medicalrecord.data.datasource.model.room.worker.MedicalWorkerDao
import cz.vvoleman.phr.feature_medicalrecord.data.mapper.*
import cz.vvoleman.phr.feature_medicalrecord.data.repository.*
import cz.vvoleman.phr.feature_medicalrecord.domain.repository.GetSelectedPatientRepository
import cz.vvoleman.phr.feature_medicalrecord.domain.repository.GetUsedMedicalWorkersRepository
import cz.vvoleman.phr.feature_medicalrecord.domain.repository.GetUsedProblemCategoriesRepository
import cz.vvoleman.phr.feature_medicalrecord.domain.repository.MedicalRecordFilterRepository
import cz.vvoleman.phr.feature_medicalrecord.domain.repository.dummy.GetDummyUsedProblemCategoriesRepository
import cz.vvoleman.phr.feature_medicalrecord.domain.repository.select_file.GetDiagnosesByIdsRepository
import cz.vvoleman.phr.feature_medicalrecord.domain.repository.select_file.GetPatientByBirthDateRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    fun providesMedicalRecordFilterRepository(
        medicalRecordDao: MedicalRecordDao,
        filterRequestDomainModelToDataMapper: FilterRequestDomainModelToDataMapper,
        medicalRecordDataSourceToDomainMapper: MedicalRecordDataSourceToDomainMapper,
    ): MedicalRecordFilterRepository =
        MedicalRecordRepository(
            medicalRecordDao,
            filterRequestDomainModelToDataMapper,
            medicalRecordDataSourceToDomainMapper
        )

    @Provides
    fun providesPatientRepository(
        patientDataStore: PatientDataStore,
        patientDataSourceToDomainMapper: PatientDataSourceToDomainMapper,
        patientDao: PatientDao,
    ) = PatientRepository(
        patientDataStore,
        patientDao,
        patientDataSourceToDomainMapper
    )

    @Provides
    fun providesGetSelectedPatientRepository(
        patientRepository: PatientRepository,
    ): GetSelectedPatientRepository = patientRepository

    @Provides
    fun providesAddressDataSourceToDomainMapper() = AddressDataSourceToDomainMapper()

    @Provides
    fun providesDiagnoseDataSourceToDomainMapper() = DiagnoseDataSourceToDomainMapper()

    @Provides
    fun providesFilterRequestDomainModelToDataSourceMapper() =
        FilterRequestDomainModelToDataMapper()

    @Provides
    fun providesMedicalRecordDataSourceToDomainMapper(
        patient: PatientDataSourceToDomainMapper,
        diagnose: DiagnoseDataSourceToDomainMapper,
        medicalWorker: MedicalWorkerDataSourceToDomainMapper,
        problemCategoryDataSourceModel: ProblemCategoryDataSourceToDomainMapper,
    ) =
        MedicalRecordDataSourceToDomainMapper(
            patient,
            diagnose,
            medicalWorker,
            problemCategoryDataSourceModel
        )

    @Provides
    fun providesMedicalWorkerDataSourceToDomainMapper(address: AddressDataSourceToDomainMapper) =
        MedicalWorkerDataSourceToDomainMapper(address)

    @Provides
    fun providesPatientDataSourceToDomainMapper() =
        PatientDataSourceToDomainMapper()

    @Provides
    fun providesProblemCategoryDataSourceToDomainMapper() =
        ProblemCategoryDataSourceToDomainMapper()

    @Provides
    fun providesGetUsedProblemCategoriesRepository(
        problemCategoryDao: ProblemCategoryDao,
        problemCategoryDataSourceToDomainMapper: ProblemCategoryDataSourceToDomainMapper,
    ): GetUsedProblemCategoriesRepository = ProblemCategoryRepository(
        problemCategoryDao,
        problemCategoryDataSourceToDomainMapper
    )

    @Provides
    fun providesGetUsedMedicalWorkersRepository(
        medicalWorkerDao: MedicalWorkerDao,
        medicalWorkerDataSourceToDomainMapper: MedicalWorkerDataSourceToDomainMapper,
    ): GetUsedMedicalWorkersRepository = MedicalWorkerRepository(
        medicalWorkerDao,
        medicalWorkerDataSourceToDomainMapper
    )

    @Provides
    fun providesGetPatientByBirthDateRepository(
        patientRepository: PatientRepository
    ): GetPatientByBirthDateRepository = patientRepository

    @Provides
    fun providesDiagnoseApiModelToDbMapper() = DiagnoseApiModelToDbMapper()

    @Provides
    fun providesGetDiagnosesByIdsRepository(
        diagnoseApiModelToDbMapper: DiagnoseApiModelToDbMapper,
        diagnoseDataSourceToDomainMapper: DiagnoseDataSourceToDomainMapper,
        diagnoseDao: DiagnoseDao,
        backendApi: BackendApi
    ): GetDiagnosesByIdsRepository = DiagnoseRepository(
        diagnoseApiModelToDbMapper,
        diagnoseDataSourceToDomainMapper,
        backendApi,
        diagnoseDao,
    )
}