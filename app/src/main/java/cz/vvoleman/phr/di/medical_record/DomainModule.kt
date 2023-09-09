package cz.vvoleman.phr.di.medical_record

import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.feature_medicalrecord.domain.mapper.TextToTextDomainModelMapper
import cz.vvoleman.phr.feature_medicalrecord.domain.repository.select_file.GetDiagnosesByIdsRepository
import cz.vvoleman.phr.feature_medicalrecord.domain.repository.select_file.GetPatientByBirthDateRepository
import cz.vvoleman.phr.feature_medicalrecord.domain.usecase.select_file.GetRecognizedOptionsFromTextUseCase
import cz.vvoleman.phr.feature_medicalrecord.domain.usecase.select_file.GetTextFromInputImageUseCase
import cz.vvoleman.phr.feature_medicalrecord.domain.usecase.select_file.ocr.RecordRecognizer
import cz.vvoleman.phr.feature_medicalrecord.domain.usecase.select_file.ocr.field.DateFieldProcessor
import cz.vvoleman.phr.feature_medicalrecord.domain.usecase.select_file.ocr.field.DiagnoseFieldProcessor
import cz.vvoleman.phr.feature_medicalrecord.domain.usecase.select_file.ocr.field.PatientFieldProcessor
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
}
