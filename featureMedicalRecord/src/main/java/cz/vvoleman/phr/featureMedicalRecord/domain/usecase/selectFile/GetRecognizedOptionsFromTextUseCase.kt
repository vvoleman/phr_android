package cz.vvoleman.phr.featureMedicalRecord.domain.usecase.selectFile

import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.base.domain.usecase.BackgroundExecutingUseCase
import cz.vvoleman.phr.featureMedicalRecord.domain.model.selectFile.RecognizedOptionsDomainModel
import cz.vvoleman.phr.featureMedicalRecord.domain.model.selectFile.TextDomainModel
import cz.vvoleman.phr.featureMedicalRecord.domain.usecase.selectFile.ocr.RecordRecognizer

class GetRecognizedOptionsFromTextUseCase(
    private val recordRecognizer: RecordRecognizer,
    coroutineContextProvider: CoroutineContextProvider
) : BackgroundExecutingUseCase<TextDomainModel, RecognizedOptionsDomainModel>(coroutineContextProvider) {

    override suspend fun executeInBackground(request: TextDomainModel): RecognizedOptionsDomainModel {
        return recordRecognizer.process(request)
    }
}
