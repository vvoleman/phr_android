package cz.vvoleman.phr.di.medical_record

import cz.vvoleman.phr.common.data.datasource.model.PatientDao
import cz.vvoleman.phr.common.data.datasource.model.PatientDataStore
import cz.vvoleman.phr.feature_medicalrecord.data.datasource.model.room.MedicalRecordDao
import cz.vvoleman.phr.feature_medicalrecord.data.datasource.model.room.category.ProblemCategoryDao
import cz.vvoleman.phr.feature_medicalrecord.data.datasource.model.room.worker.MedicalWorkerDao
import cz.vvoleman.phr.feature_medicalrecord.data.mapper.*
import cz.vvoleman.phr.feature_medicalrecord.data.repository.MedicalRecordRepository
import cz.vvoleman.phr.feature_medicalrecord.data.repository.MedicalWorkerRepository
import cz.vvoleman.phr.feature_medicalrecord.data.repository.PatientRepository
import cz.vvoleman.phr.feature_medicalrecord.data.repository.ProblemCategoryRepository
import cz.vvoleman.phr.feature_medicalrecord.domain.repository.GetSelectedPatientRepository
import cz.vvoleman.phr.feature_medicalrecord.domain.repository.GetUsedMedicalWorkersRepository
import cz.vvoleman.phr.feature_medicalrecord.domain.repository.GetUsedProblemCategoriesRepository
import cz.vvoleman.phr.feature_medicalrecord.domain.repository.MedicalRecordFilterRepository
import cz.vvoleman.phr.feature_medicalrecord.domain.repository.dummy.GetDummyUsedProblemCategoriesRepository
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
    fun providesGetSelectedPatientRepository(
        patientDataStore: PatientDataStore,
        patientDataSourceToDomainMapper: PatientDataSourceToDomainMapper,
        patientDao: PatientDao,
    ): GetSelectedPatientRepository =
        PatientRepository(patientDataStore, patientDao, patientDataSourceToDomainMapper)

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
}