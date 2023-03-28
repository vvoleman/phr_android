package cz.vvoleman.phr.di.medical_record

import android.content.Context
import cz.vvoleman.phr.common.data.datasource.model.PatientDao
import cz.vvoleman.phr.common.data.datasource.model.PatientDataStore
import cz.vvoleman.phr.feature_medicalrecord.data.datasource.model.retrofit.BackendApi
import cz.vvoleman.phr.feature_medicalrecord.data.datasource.model.room.MedicalRecordDao
import cz.vvoleman.phr.feature_medicalrecord.data.datasource.model.room.asset.MedicalRecordAssetDao
import cz.vvoleman.phr.feature_medicalrecord.data.datasource.model.room.category.ProblemCategoryDao
import cz.vvoleman.phr.feature_medicalrecord.data.datasource.model.room.diagnose.DiagnoseDao
import cz.vvoleman.phr.feature_medicalrecord.data.datasource.model.room.worker.MedicalWorkerDao
import cz.vvoleman.phr.feature_medicalrecord.data.mapper.*
import cz.vvoleman.phr.feature_medicalrecord.data.repository.*
import cz.vvoleman.phr.feature_medicalrecord.domain.repository.*
import cz.vvoleman.phr.feature_medicalrecord.domain.repository.add_edit.SearchDiagnoseRepository
import cz.vvoleman.phr.feature_medicalrecord.domain.repository.dummy.GetDummyUsedProblemCategoriesRepository
import cz.vvoleman.phr.feature_medicalrecord.domain.repository.select_file.GetDiagnosesByIdsRepository
import cz.vvoleman.phr.feature_medicalrecord.domain.repository.select_file.GetPatientByBirthDateRepository
import cz.vvoleman.phr.feature_medicalrecord.domain.repository.select_file.SaveFileRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
        asset: MedicalRecordAssetDataSourceToDomainMapper,
    ) =
        MedicalRecordDataSourceToDomainMapper(
            patient,
            diagnose,
            medicalWorker,
            problemCategoryDataSourceModel,
            asset
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
        diagnoseRepository: DiagnoseRepository
    ): GetDiagnosesByIdsRepository = diagnoseRepository

    @Provides
    fun providesDiagnoseRepository(
        diagnoseApiModelToDbMapper: DiagnoseApiModelToDbMapper,
        diagnoseDataSourceToDomainMapper: DiagnoseDataSourceToDomainMapper,
        diagnoseDao: DiagnoseDao,
        backendApi: BackendApi
    )= DiagnoseRepository(
        diagnoseApiModelToDbMapper,
        diagnoseDataSourceToDomainMapper,
        backendApi,
        diagnoseDao,
    )

    @Provides
    fun providesSaveFileRepository(
        @ApplicationContext context: Context
    ): SaveFileRepository = FileRepository(context)

    @Provides
    fun providesCreateMedicalRecordAssetRepository(
        domainToDataSourceMapper: MedicalRecordAssetDomainToDataSourceMapper,
        assetDao: MedicalRecordAssetDao
    ): CreateMedicalRecordAssetRepository =
        MedicalRecordAssetRepository(domainToDataSourceMapper, assetDao)

    @Provides
    fun providesMedicalRecordAssetDataSourceToDomainMapper() =
        MedicalRecordAssetDataSourceToDomainMapper()

    @Provides
    fun providesMedicalRecordAssetDomainToDataSourceMapper() =
        MedicalRecordAssetDomainToDataSourceMapper()

    @Provides
    fun providesGetPatientByIdRepository(
        patientRepository: PatientRepository
    ): GetPatientByIdRepository = patientRepository

    @Provides
    fun providesGetDiagnoseByIdRepository(
        diagnoseRepository: DiagnoseRepository
    ): GetDiagnoseByIdRepository = diagnoseRepository

    @Provides
    fun providesSearchDiagnoseRepository(
        diagnoseRepository: DiagnoseRepository
    ): SearchDiagnoseRepository = diagnoseRepository
}