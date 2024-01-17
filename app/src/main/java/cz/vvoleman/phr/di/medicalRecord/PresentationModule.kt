package cz.vvoleman.phr.di.medicalRecord

import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.common.domain.repository.patient.GetPatientByIdRepository
import cz.vvoleman.phr.common.domain.repository.patient.GetSelectedPatientRepository
import cz.vvoleman.phr.common.presentation.mapper.PatientPresentationModelToDomainMapper
import cz.vvoleman.phr.featureMedicalRecord.domain.repository.AddEditMedicalRecordRepository
import cz.vvoleman.phr.featureMedicalRecord.domain.repository.CreateDiagnoseRepository
import cz.vvoleman.phr.featureMedicalRecord.domain.repository.CreateMedicalRecordAssetRepository
import cz.vvoleman.phr.featureMedicalRecord.domain.repository.GetDiagnoseByIdRepository
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
import cz.vvoleman.phr.featureMedicalRecord.domain.repository.selectFile.SaveFileRepository
import cz.vvoleman.phr.featureMedicalRecord.domain.usecase.AddEditMedicalRecordUseCase
import cz.vvoleman.phr.featureMedicalRecord.domain.usecase.DeletePatientUseCase
import cz.vvoleman.phr.featureMedicalRecord.domain.usecase.GetFilteredRecordsUseCase
import cz.vvoleman.phr.featureMedicalRecord.domain.usecase.GetRecordByIdUseCase
import cz.vvoleman.phr.featureMedicalRecord.domain.usecase.GetUsedMedicalWorkersUseCase
import cz.vvoleman.phr.featureMedicalRecord.domain.usecase.GetUsedProblemCategoriesUseCase
import cz.vvoleman.phr.featureMedicalRecord.domain.usecase.GetUserListsUseCase
import cz.vvoleman.phr.featureMedicalRecord.domain.usecase.addEdit.SearchDiagnoseUseCase
import cz.vvoleman.phr.featureMedicalRecord.domain.usecase.selectFile.GetDataForSelectedOptionsUseCase
import cz.vvoleman.phr.featureMedicalRecord.domain.usecase.selectFile.SaveMedicalRecordFileUseCase
import cz.vvoleman.phr.featureMedicalRecord.presentation.addEdit.mapper.AddEditPresentationModelToDomainMapper
import cz.vvoleman.phr.featureMedicalRecord.presentation.addEdit.mapper.AddEditViewStateToModelMapper
import cz.vvoleman.phr.featureMedicalRecord.presentation.addEdit.mapper.AssetPresentationToDomainModelMapper
import cz.vvoleman.phr.featureMedicalRecord.presentation.addEdit.mapper.DiagnosePresentationModelToDomainMapper
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
    fun providesDiagnoseDomainToPresentationMapper() = DiagnosePresentationModelToDomainMapper()

    @Provides
    fun providesRecognizedOptionsDomainModelToPresentationMapper(
        diagnoseMapper: DiagnosePresentationModelToDomainMapper,
        patientMapper: PatientPresentationModelToDomainMapper
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
        createDiagnoseRepository: CreateDiagnoseRepository,
        coroutineContextProvider: CoroutineContextProvider
    ) = AddEditMedicalRecordUseCase(
        addEditMedicalRecordRepository,
        createDiagnoseRepository,
        coroutineContextProvider
    )

    @Provides
    fun providesAddEditPresentationModelToDomainMapper(
        assetMapper: AssetPresentationToDomainModelMapper,
        diagnoseMapper: DiagnosePresentationModelToDomainMapper,
    ) = AddEditPresentationModelToDomainMapper(assetMapper, diagnoseMapper)

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

    @Provides
    fun providesAddEditViewStateToModelMapper() = AddEditViewStateToModelMapper()
}
