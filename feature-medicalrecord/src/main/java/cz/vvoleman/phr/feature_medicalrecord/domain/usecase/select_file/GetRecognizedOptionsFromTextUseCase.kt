package cz.vvoleman.phr.feature_medicalrecord.domain.usecase.select_file

import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.base.domain.usecase.BackgroundExecutingUseCase
import cz.vvoleman.phr.feature_medicalrecord.domain.model.select_file.RecognizedOptionsDomainModel
import cz.vvoleman.phr.feature_medicalrecord.domain.model.select_file.TextDomainModel
import cz.vvoleman.phr.feature_medicalrecord.domain.usecase.select_file.ocr.RecordRecognizer

class GetRecognizedOptionsFromTextUseCase(
    private val recordRecognizer: RecordRecognizer,
    coroutineContextProvider: CoroutineContextProvider
) : BackgroundExecutingUseCase<TextDomainModel, RecognizedOptionsDomainModel>(coroutineContextProvider) {

    override suspend fun executeInBackground(request: TextDomainModel): RecognizedOptionsDomainModel {
        return recordRecognizer.process(request)
    }
}
