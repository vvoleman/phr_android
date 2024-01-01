package cz.vvoleman.phr.di.medicalRecord

import android.content.Context
import cz.vvoleman.phr.common.data.datasource.model.healthcare.worker.MedicalWorkerDao
import cz.vvoleman.phr.common.data.datasource.model.healthcare.worker.SpecificMedicalWorkerDao
import cz.vvoleman.phr.common.data.datasource.model.problemCategory.ProblemCategoryDao
import cz.vvoleman.phr.common.data.mapper.PatientDataSourceModelToDomainMapper
import cz.vvoleman.phr.common.data.mapper.healthcare.MedicalWorkerDataSourceModelToDomainMapper
import cz.vvoleman.phr.common.data.mapper.healthcare.SpecificMedicalWorkerDataSourceToDomainMapper
import cz.vvoleman.phr.common.data.mapper.problemCategory.ProblemCategoryDataSourceModelToDomainMapper
import cz.vvoleman.phr.featureMedicalRecord.data.datasource.model.retrofit.BackendApi
import cz.vvoleman.phr.featureMedicalRecord.data.datasource.model.room.MedicalRecordDao
import cz.vvoleman.phr.featureMedicalRecord.data.datasource.model.room.asset.MedicalRecordAssetDao
import cz.vvoleman.phr.featureMedicalRecord.data.datasource.model.room.diagnose.DiagnoseDao
import cz.vvoleman.phr.featureMedicalRecord.data.mapper.AddEditDomainModelToToDataSourceMapper
import cz.vvoleman.phr.featureMedicalRecord.data.mapper.AddressDataSourceToDomainMapper
import cz.vvoleman.phr.featureMedicalRecord.data.mapper.DiagnoseApiModelToDbMapper
import cz.vvoleman.phr.featureMedicalRecord.data.mapper.DiagnoseDataSourceToDomainMapper
import cz.vvoleman.phr.featureMedicalRecord.data.mapper.FilterRequestDomainModelToDataMapper
import cz.vvoleman.phr.featureMedicalRecord.data.mapper.MedicalRecordAssetDataSourceToDomainMapper
import cz.vvoleman.phr.featureMedicalRecord.data.mapper.MedicalRecordAssetDomainToDataSourceMapper
import cz.vvoleman.phr.featureMedicalRecord.data.mapper.MedicalRecordDataSourceToDomainMapper
import cz.vvoleman.phr.featureMedicalRecord.data.repository.DeletePatientRepository
import cz.vvoleman.phr.featureMedicalRecord.data.repository.DiagnoseRepository
import cz.vvoleman.phr.featureMedicalRecord.data.repository.FileRepository
import cz.vvoleman.phr.featureMedicalRecord.data.repository.MedicalRecordAssetRepository
import cz.vvoleman.phr.featureMedicalRecord.data.repository.MedicalRecordRepository
import cz.vvoleman.phr.featureMedicalRecord.data.repository.MedicalWorkerRepository
import cz.vvoleman.phr.featureMedicalRecord.data.repository.ProblemCategoryRepository
import cz.vvoleman.phr.featureMedicalRecord.domain.repository.AddEditMedicalRecordRepository
import cz.vvoleman.phr.featureMedicalRecord.domain.repository.CreateMedicalRecordAssetRepository
import cz.vvoleman.phr.featureMedicalRecord.domain.repository.GetDiagnoseByIdRepository
import cz.vvoleman.phr.featureMedicalRecord.domain.repository.GetMedicalRecordByFacilityRepository
import cz.vvoleman.phr.featureMedicalRecord.domain.repository.GetMedicalRecordByMedicalWorkerRepository
import cz.vvoleman.phr.featureMedicalRecord.domain.repository.GetMedicalWorkersForPatientRepository
import cz.vvoleman.phr.featureMedicalRecord.domain.repository.GetProblemCategoriesForPatientRepository
import cz.vvoleman.phr.featureMedicalRecord.domain.repository.GetRecordByIdRepository
import cz.vvoleman.phr.featureMedicalRecord.domain.repository.GetUsedMedicalWorkersRepository
import cz.vvoleman.phr.featureMedicalRecord.domain.repository.GetUsedProblemCategoriesRepository
import cz.vvoleman.phr.featureMedicalRecord.domain.repository.MedicalRecordFilterRepository
import cz.vvoleman.phr.featureMedicalRecord.domain.repository.addEdit.SearchDiagnoseRepository
import cz.vvoleman.phr.featureMedicalRecord.domain.repository.patientDelete.DeleteMedicalRecordAssetsRepository
import cz.vvoleman.phr.featureMedicalRecord.domain.repository.patientDelete.DeleteMedicalRecordsRepository
import cz.vvoleman.phr.featureMedicalRecord.domain.repository.patientDelete.DeleteMedicalWorkersRepository
import cz.vvoleman.phr.featureMedicalRecord.domain.repository.patientDelete.DeleteProblemCategoriesRepository
import cz.vvoleman.phr.featureMedicalRecord.domain.repository.selectFile.GetDiagnosesByIdsRepository
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
        problemCategoryDataSourceModel: ProblemCategoryDataSourceModelToDomainMapper,
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
    fun providesProblemCategoryRepository(
        medicalRecordDao: MedicalRecordDao,
        problemCategoryDao: ProblemCategoryDao,
        problemCategoryMapper: ProblemCategoryDataSourceModelToDomainMapper
    ) = ProblemCategoryRepository(
        medicalRecordDao,
        problemCategoryDao,
        problemCategoryMapper
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

    @Provides
    fun providesGetMedicalRecordByMedicalWorkerRepository(
        medicalRecordRepository: MedicalRecordRepository
    ): GetMedicalRecordByMedicalWorkerRepository = medicalRecordRepository

    @Provides
    fun providesGetMedicalRecordByFacilityRepository(
        medicalRecordRepository: MedicalRecordRepository
    ): GetMedicalRecordByFacilityRepository = medicalRecordRepository

    @Provides
    fun providesProblemCategoryDataSourceModelToDomainMapper() =
        ProblemCategoryDataSourceModelToDomainMapper()
}
