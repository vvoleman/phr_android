package cz.vvoleman.phr.di.medicalRecord

import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.common.domain.repository.patient.GetPatientByIdRepository
import cz.vvoleman.phr.common.domain.repository.patient.GetSelectedPatientRepository
import cz.vvoleman.phr.featureMedicalRecord.domain.repository.*
import cz.vvoleman.phr.featureMedicalRecord.domain.repository.addEdit.SearchDiagnoseRepository
import cz.vvoleman.phr.featureMedicalRecord.domain.repository.patientDelete.DeleteMedicalRecordAssetsRepository
import cz.vvoleman.phr.featureMedicalRecord.domain.repository.patientDelete.DeleteMedicalRecordsRepository
import cz.vvoleman.phr.featureMedicalRecord.domain.repository.patientDelete.DeleteMedicalWorkersRepository
import cz.vvoleman.phr.featureMedicalRecord.domain.repository.patientDelete.DeleteProblemCategoriesRepository
import cz.vvoleman.phr.featureMedicalRecord.domain.repository.selectFile.SaveFileRepository
import cz.vvoleman.phr.featureMedicalRecord.domain.usecase.*
import cz.vvoleman.phr.featureMedicalRecord.domain.usecase.addEdit.SearchDiagnoseUseCase
import cz.vvoleman.phr.featureMedicalRecord.domain.usecase.selectFile.GetDataForSelectedOptionsUseCase
import cz.vvoleman.phr.featureMedicalRecord.domain.usecase.selectFile.SaveMedicalRecordFileUseCase
import cz.vvoleman.phr.featureMedicalRecord.presentation.addEdit.mapper.AddEditPresentationModelToDomainMapper
import cz.vvoleman.phr.featureMedicalRecord.presentation.addEdit.mapper.AssetPresentationToDomainModelMapper
import cz.vvoleman.phr.featureMedicalRecord.presentation.addEdit.mapper.DiagnoseDomainModelToPresentationMapper
import cz.vvoleman.phr.featureMedicalRecord.presentation.addEdit.mapper.PatientDomainModelToPresentationMapper
import cz.vvoleman.phr.featureMedicalRecord.presentation.list.mapper.ListViewStateToDomainMapper
import cz.vvoleman.phr.featureMedicalRecord.presentation.selectFile.mapper.RecognizedOptionsDomainModelToPresentationMapper
import cz.vvoleman.phr.featureMedicalRecord.presentation.selectFile.mapper.SelectedOptionsPresentationToDomainMapper
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

    @Provides
    fun providesDeletePatientUseCase(
        deleteMedicalRecordAssetsRepository: DeleteMedicalRecordAssetsRepository,
        deleteMedicalRecordsRepository: DeleteMedicalRecordsRepository,
        deleteMedicalWorkersRepository: DeleteMedicalWorkersRepository,
        deleteProblemCategoriesRepository: DeleteProblemCategoriesRepository,
        coroutineContextProvider: CoroutineContextProvider
    ) = DeletePatientUseCase(
        deleteMedicalRecordAssetsRepository,
        deleteMedicalRecordsRepository,
        deleteMedicalWorkersRepository,
        deleteProblemCategoriesRepository,
        coroutineContextProvider
    )
}
