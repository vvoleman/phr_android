package cz.vvoleman.phr.di.medical_record

import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.feature_medicalrecord.data.repository.MedicalRecordRepository
import cz.vvoleman.phr.feature_medicalrecord.data.repository.MedicalWorkerRepository
import cz.vvoleman.phr.feature_medicalrecord.data.repository.ProblemCategoryRepository
import cz.vvoleman.phr.feature_medicalrecord.domain.repository.*
import cz.vvoleman.phr.feature_medicalrecord.domain.repository.add_edit.SearchDiagnoseRepository
import cz.vvoleman.phr.feature_medicalrecord.domain.repository.select_file.SaveFileRepository
import cz.vvoleman.phr.feature_medicalrecord.domain.usecase.*
import cz.vvoleman.phr.feature_medicalrecord.domain.usecase.add_edit.SearchDiagnoseUseCase
import cz.vvoleman.phr.feature_medicalrecord.domain.usecase.select_file.GetDataForSelectedOptionsUseCase
import cz.vvoleman.phr.feature_medicalrecord.domain.usecase.select_file.SaveMedicalRecordFileUseCase
import cz.vvoleman.phr.feature_medicalrecord.presentation.addedit.mapper.AddEditPresentationModelToDomainMapper
import cz.vvoleman.phr.feature_medicalrecord.presentation.addedit.mapper.AssetPresentationToDomainModelMapper
import cz.vvoleman.phr.feature_medicalrecord.presentation.addedit.mapper.DiagnoseDomainModelToPresentationMapper
import cz.vvoleman.phr.feature_medicalrecord.presentation.addedit.mapper.PatientDomainModelToPresentationMapper
import cz.vvoleman.phr.feature_medicalrecord.presentation.list.mapper.ListViewStateToDomainMapper
import cz.vvoleman.phr.feature_medicalrecord.presentation.select_file.mapper.RecognizedOptionsDomainModelToPresentationMapper
import cz.vvoleman.phr.feature_medicalrecord.presentation.select_file.mapper.SelectedOptionsPresentationToDomainMapper
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
        getSelectedPatientRepository: GetSelectedPatientRepository,
        coroutineContextProvider: CoroutineContextProvider
    ) = GetFilteredRecordsUseCase(
        medicalRecordRepository,
        getSelectedPatientRepository,
        coroutineContextProvider
    )

    @Provides
    fun providesGetSelectedPatientUseCase(
        getSelectedPatientRepository: GetSelectedPatientRepository
    ) = GetSelectedPatientUseCase(
        getSelectedPatientRepository
    )

    @Provides
    fun providesGetUsedProblemCategoriesUseCase(
        getUsedProblemCategoriesRepository: GetUsedProblemCategoriesRepository,
        coroutineContextProvider: CoroutineContextProvider
    ) = GetUsedProblemCategoriesUseCase(
        getUsedProblemCategoriesRepository,
        coroutineContextProvider
    )

    @Provides
    fun providesGetUsedMedicalWorkersUseCase(
        getUsedMedicalWorkersRepository: GetUsedMedicalWorkersRepository,
        coroutineContextProvider: CoroutineContextProvider
    ) = GetUsedMedicalWorkersUseCase(
        getUsedMedicalWorkersRepository,
        coroutineContextProvider
    )

    @Provides
    fun providesPatientDomainToPresentationMapper() = PatientDomainModelToPresentationMapper()

    @Provides
    fun providesListViewStateToDomainMapper() = ListViewStateToDomainMapper()

    @Provides
    fun providesDiagnoseDomainToPresentationMapper() = DiagnoseDomainModelToPresentationMapper()

    @Provides
    fun providesRecognizedOptionsDomainModelToPresentationMapper(
        diagnoseMapper: DiagnoseDomainModelToPresentationMapper,
        patientMapper: PatientDomainModelToPresentationMapper
    ) = RecognizedOptionsDomainModelToPresentationMapper(
        diagnoseMapper,
        patientMapper
    )

    @Provides
    fun providesSaveMedicalRecordFileUseCase(
        createMedicalRecordAssetRepository: CreateMedicalRecordAssetRepository,
        saveFileRepository: SaveFileRepository,
        coroutineContextProvider: CoroutineContextProvider
    ) = SaveMedicalRecordFileUseCase(
        createMedicalRecordAssetRepository,
        saveFileRepository,
        coroutineContextProvider
    )

    @Provides
    fun providesGetDataForSelectedOptionsUseCase(
        getDiagnoseByIdRepository: GetDiagnoseByIdRepository,
        getPatientByIdRepository: GetPatientByIdRepository,
        coroutineContextProvider: CoroutineContextProvider
    ) = GetDataForSelectedOptionsUseCase(
        getDiagnoseByIdRepository,
        getPatientByIdRepository,
        coroutineContextProvider
    )

    @Provides
    fun providesSelectedOptionsPresentationToDomainMapper() =
        SelectedOptionsPresentationToDomainMapper()

    @Provides
    fun providesSearchDiagnoseUseCase(
        searchDiagnoseRepository: SearchDiagnoseRepository,
        coroutineContextProvider: CoroutineContextProvider
    ) = SearchDiagnoseUseCase(
        searchDiagnoseRepository,
        coroutineContextProvider
    )

    @Provides
    fun providesGetUserListsUseCase(
        getProblemCategoriesForPatientRepository: GetProblemCategoriesForPatientRepository,
        getMedicalWorkersForPatientRepository: GetMedicalWorkersForPatientRepository,
        coroutineContextProvider: CoroutineContextProvider
    ) = GetUserListsUseCase(
        getProblemCategoriesForPatientRepository,
        getMedicalWorkersForPatientRepository,
        coroutineContextProvider
    )

    @Provides
    fun providesGetRecordByIdUseCase(
        getRecordByIdRepository: GetRecordByIdRepository,
        coroutineContextProvider: CoroutineContextProvider
    ) = GetRecordByIdUseCase(
        getRecordByIdRepository,
        coroutineContextProvider
    )

    @Provides
    fun providesAddEditMedicalRecordUseCase(
        addEditMedicalRecordRepository: AddEditMedicalRecordRepository,
        coroutineContextProvider: CoroutineContextProvider
    ) = AddEditMedicalRecordUseCase(
        addEditMedicalRecordRepository,
        coroutineContextProvider
    )

    @Provides
    fun providesAddEditPresentationModelToDomainMapper(
        assetPresentationToDomainModelMapper: AssetPresentationToDomainModelMapper
    ) = AddEditPresentationModelToDomainMapper(assetPresentationToDomainModelMapper)

    @Provides
    fun providesAssetPresentationToDomainMapper() = AssetPresentationToDomainModelMapper()

}