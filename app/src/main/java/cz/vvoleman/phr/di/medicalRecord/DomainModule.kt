package cz.vvoleman.phr.di.medicalRecord

import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.common.domain.repository.patient.GetPatientByBirthDateRepository
import cz.vvoleman.phr.featureMedicalRecord.domain.mapper.TextToTextDomainModelMapper
import cz.vvoleman.phr.featureMedicalRecord.domain.repository.DeleteMedicalRecordRepository
import cz.vvoleman.phr.featureMedicalRecord.domain.repository.patientDelete.DeleteMedicalRecordAssetsRepository
import cz.vvoleman.phr.featureMedicalRecord.domain.repository.selectFile.GetDiagnosesByIdsRepository
import cz.vvoleman.phr.featureMedicalRecord.domain.usecase.DeleteMedicalRecordUseCase
import cz.vvoleman.phr.featureMedicalRecord.domain.usecase.selectFile.GetRecognizedOptionsFromTextUseCase
import cz.vvoleman.phr.featureMedicalRecord.domain.usecase.selectFile.GetTextFromInputImageUseCase
import cz.vvoleman.phr.featureMedicalRecord.domain.usecase.selectFile.ocr.RecordRecognizer
import cz.vvoleman.phr.featureMedicalRecord.domain.usecase.selectFile.ocr.field.DateFieldProcessor
import cz.vvoleman.phr.featureMedicalRecord.domain.usecase.selectFile.ocr.field.DiagnoseFieldProcessor
import cz.vvoleman.phr.featureMedicalRecord.domain.usecase.selectFile.ocr.field.PatientFieldProcessor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {

    @Provides
    fun providesGetRecognizedOptionsFromTextUseCase(
        recordRecognizer: RecordRecognizer,
        coroutineContextProvider: CoroutineContextProvider
    ) = GetRecognizedOptionsFromTextUseCase(
        recordRecognizer,
        coroutineContextProvider
    )

    @Provides
    fun providesGetTextFromInputImageUseCase(
        textToTextDomainModelMapper: TextToTextDomainModelMapper
    ) = GetTextFromInputImageUseCase(textToTextDomainModelMapper)

    @Provides
    fun providesTextToDomainModelMapper() = TextToTextDomainModelMapper()

    @Provides
    fun providesPatientFieldProcessor(getPatientByBirthDateRepository: GetPatientByBirthDateRepository) =
        PatientFieldProcessor(getPatientByBirthDateRepository)

    @Provides
    fun providesDateFieldProcessor() = DateFieldProcessor()

    @Provides
    fun providesDiagnoseFieldProcessor(
        getDiagnosesByIdsRepository: GetDiagnosesByIdsRepository
    ) = DiagnoseFieldProcessor(getDiagnosesByIdsRepository)

    @Provides
    fun providesRecordRecognizer(
        patientFieldProcessor: PatientFieldProcessor,
        dateFieldProcessor: DateFieldProcessor,
        diagnoseFieldProcessor: DiagnoseFieldProcessor
    ) = RecordRecognizer(patientFieldProcessor, dateFieldProcessor, diagnoseFieldProcessor)

    @Provides
    fun providesDeleteMedicalRecordUseCase(
        deleteMedicalRecordRepository: DeleteMedicalRecordRepository,
        deleteMedicalRecordAssetsRepository: DeleteMedicalRecordAssetsRepository,
        coroutineContextProvider: CoroutineContextProvider,
    ) = DeleteMedicalRecordUseCase(
        deleteMedicalRecord = deleteMedicalRecordRepository,
        deleteMedicalRecordAssetsRepository = deleteMedicalRecordAssetsRepository,
        coroutineContextProvider = coroutineContextProvider
    )
}
