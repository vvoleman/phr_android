package cz.vvoleman.phr.di.medicalRecord

import android.content.Context
import cz.vvoleman.phr.common.data.datasource.model.PatientDao
import cz.vvoleman.phr.common.data.datasource.model.healthcare.worker.MedicalWorkerDao
import cz.vvoleman.phr.common.data.datasource.model.healthcare.worker.SpecificMedicalWorkerDao
import cz.vvoleman.phr.common.data.mapper.healthcare.MedicalWorkerDataSourceModelToDomainMapper
import cz.vvoleman.phr.common.data.mapper.healthcare.SpecificMedicalWorkerDataSourceToDomainMapper
import cz.vvoleman.phr.featureMedicalRecord.data.datasource.model.retrofit.BackendApi
import cz.vvoleman.phr.featureMedicalRecord.data.datasource.model.room.MedicalRecordDao
import cz.vvoleman.phr.featureMedicalRecord.data.datasource.model.room.asset.MedicalRecordAssetDao
import cz.vvoleman.phr.featureMedicalRecord.data.datasource.model.room.category.ProblemCategoryDao
import cz.vvoleman.phr.featureMedicalRecord.data.datasource.model.room.diagnose.DiagnoseDao
import cz.vvoleman.phr.featureMedicalRecord.data.mapper.*
import cz.vvoleman.phr.featureMedicalRecord.data.repository.*
import cz.vvoleman.phr.featureMedicalRecord.domain.repository.*
import cz.vvoleman.phr.featureMedicalRecord.domain.repository.addEdit.SearchDiagnoseRepository
import cz.vvoleman.phr.featureMedicalRecord.domain.repository.patientDelete.DeleteMedicalRecordAssetsRepository
import cz.vvoleman.phr.featureMedicalRecord.domain.repository.patientDelete.DeleteMedicalRecordsRepository
import cz.vvoleman.phr.featureMedicalRecord.domain.repository.patientDelete.DeleteMedicalWorkersRepository
import cz.vvoleman.phr.featureMedicalRecord.domain.repository.patientDelete.DeleteProblemCategoriesRepository
import cz.vvoleman.phr.featureMedicalRecord.domain.repository.selectFile.GetDiagnosesByIdsRepository
import cz.vvoleman.phr.featureMedicalRecord.domain.repository.selectFile.GetPatientByBirthDateRepository
import cz.vvoleman.phr.featureMedicalRecord.domain.repository.selectFile.SaveFileRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    fun providesMedicalRecordRepository(
        medicalRecordDao: MedicalRecordDao,
        filterRequestDomainModelToDataMapper: FilterRequestDomainModelToDataMapper,
        medicalRecordDataSourceToDomainMapper: MedicalRecordDataSourceToDomainMapper,
        addEditDomainModelToToDataSourceMapper: AddEditDomainModelToToDataSourceMapper
    ) = MedicalRecordRepository(
        medicalRecordDao,
        filterRequestDomainModelToDataMapper,
        medicalRecordDataSourceToDomainMapper,
        addEditDomainModelToToDataSourceMapper
    )

    @Provides
    fun providesMedicalRecordFilterRepository(
        medicalRecordRepository: MedicalRecordRepository
    ): MedicalRecordFilterRepository =
        medicalRecordRepository

    @Provides
    fun providesGetRecordByIdRepository(
        medicalRecordRepository: MedicalRecordRepository
    ): GetRecordByIdRepository = medicalRecordRepository

    @Provides
    fun providesPatientRepository(
        patientDataSourceToDomainMapper: PatientDataSourceModelToDomainMapper,
        patientDao: PatientDao
    ) = PatientRepository(
        patientDao,
        patientDataSourceToDomainMapper
    )

    @Provides
    fun providesAddressDataSourceToDomainMapper() = AddressDataSourceToDomainMapper()

    @Provides
    fun providesDiagnoseDataSourceToDomainMapper() = DiagnoseDataSourceToDomainMapper()

    @Provides
    fun providesFilterRequestDomainModelToDataSourceMapper() =
        FilterRequestDomainModelToDataMapper()

    @Provides
    fun providesMedicalRecordDataSourceToDomainMapper(
        patient: PatientDataSourceModelToDomainMapper,
        diagnose: DiagnoseDataSourceToDomainMapper,
        specificWorker: SpecificMedicalWorkerDataSourceToDomainMapper,
        problemCategoryDataSourceModel: ProblemCategoryDataSourceToDomainMapper,
        specificWorkerDao: SpecificMedicalWorkerDao
    ) =
        MedicalRecordDataSourceToDomainMapper(
            patient,
            diagnose,
            specificWorker,
            problemCategoryDataSourceModel,
            specificWorkerDao
        )

    @Provides
    fun providesPatientDataSourceToDomainMapper() =
        PatientDataSourceModelToDomainMapper()

    @Provides
    fun providesProblemCategoryDataSourceToDomainMapper() =
        ProblemCategoryDataSourceToDomainMapper()

    @Provides
    fun providesProblemCategoryRepository(
        problemCategoryDao: ProblemCategoryDao,
        problemCategoryDataSourceToDomainMapper: ProblemCategoryDataSourceToDomainMapper
    ) = ProblemCategoryRepository(
        problemCategoryDao,
        problemCategoryDataSourceToDomainMapper
    )

    @Provides
    fun providesGetUsedProblemCategoriesRepository(
        problemCategoryRepository: ProblemCategoryRepository
    ): GetUsedProblemCategoriesRepository = problemCategoryRepository

    @Provides
    fun providesMedicalWorkersRepository(
        medicalRecordDao: MedicalRecordDao,
        medicalWorkerDao: MedicalWorkerDao,
        workerMapper: MedicalWorkerDataSourceModelToDomainMapper,
        specificWorkerMapper: SpecificMedicalWorkerDataSourceToDomainMapper
    ) = MedicalWorkerRepository(
        medicalRecordDao,
        medicalWorkerDao,
        workerMapper,
        specificWorkerMapper
    )

    @Provides
    fun providesGetUsedMedicalWorkersRepository(
        medicalWorkerRepository: MedicalWorkerRepository
    ): GetUsedMedicalWorkersRepository = medicalWorkerRepository

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
    ) = DiagnoseRepository(
        diagnoseApiModelToDbMapper,
        diagnoseDataSourceToDomainMapper,
        backendApi,
        diagnoseDao
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
    fun providesGetDiagnoseByIdRepository(
        diagnoseRepository: DiagnoseRepository
    ): GetDiagnoseByIdRepository = diagnoseRepository

    @Provides
    fun providesSearchDiagnoseRepository(
        diagnoseRepository: DiagnoseRepository
    ): SearchDiagnoseRepository = diagnoseRepository

    @Provides
    fun providesGetProblemCategoriesForPatientRepository(
        problemCategoryRepository: ProblemCategoryRepository
    ): GetProblemCategoriesForPatientRepository = problemCategoryRepository

    @Provides
    fun providesGetMedicalWorkersForPatientRepository(
        medicalWorkerRepository: MedicalWorkerRepository
    ): GetMedicalWorkersForPatientRepository = medicalWorkerRepository

    @Provides
    fun providesAddEditMedicalRecordRepository(
        medicalRecordRepository: MedicalRecordRepository
    ): AddEditMedicalRecordRepository = medicalRecordRepository

    @Provides
    fun providesAddEditDomainModelToDataSourceMapper() =
        AddEditDomainModelToToDataSourceMapper()

    @Provides
    fun providesDeletePatientRepository(
        medicalRecordDao: MedicalRecordDao,
        medicalRecordAssetDao: MedicalRecordAssetDao,
        medicalWorkerDao: MedicalWorkerDao,
        problemCategoryDao: ProblemCategoryDao
    ) = DeletePatientRepository(
        medicalRecordDao,
        medicalWorkerDao,
        medicalRecordAssetDao,
        problemCategoryDao
    )

    @Provides
    fun providesDeleteMedicalRecordAssetRepository(
        deletePatientRepository: DeletePatientRepository
    ): DeleteMedicalRecordAssetsRepository = deletePatientRepository

    @Provides
    fun providesDeleteMedicalRecordsRepository(
        deletePatientRepository: DeletePatientRepository
    ): DeleteMedicalRecordsRepository = deletePatientRepository

    @Provides
    fun providesDeleteMedicalWorkersRepository(
        deletePatientRepository: DeletePatientRepository
    ): DeleteMedicalWorkersRepository = deletePatientRepository

    @Provides
    fun providesDeleteProblemCategoriesRepository(
        deletePatientRepository: DeletePatientRepository
    ): DeleteProblemCategoriesRepository = deletePatientRepository
}
