package cz.vvoleman.phr.feature_medicalrecord.domain.usecase.select_file.ocr.field

import android.util.Log
import cz.vvoleman.phr.feature_medicalrecord.domain.model.select_file.TextDomainModel
import cz.vvoleman.phr.feature_medicalrecord.domain.model.select_file.result.DiagnoseFieldResultDomainModel
import cz.vvoleman.phr.feature_medicalrecord.domain.repository.select_file.GetDiagnosesByIdsRepository
import cz.vvoleman.phr.feature_medicalrecord.domain.usecase.select_file.ocr.FieldProcessor

class DiagnoseFieldProcessor(
    private val getDiagnosesByIdsRepository: GetDiagnosesByIdsRepository
) : FieldProcessor<DiagnoseFieldResultDomainModel> {

    private val regexp = "[A-TV-Z][0-9][0-9AB]\\.?[0-9A-TV-Z]{0,4}"

    override suspend fun process(text: TextDomainModel): List<DiagnoseFieldResultDomainModel> {
        val textValue = text.getText()

        val matches = regexp.toRegex().findAll(textValue)

        val ids = matches.map { it.value }.toList()

        Log.d("DiagnoseFieldProcessor", "Found ids: $ids")

        return getDiagnosesByIdsRepository
            .getDiagnosesByIds(ids)
            .map { DiagnoseFieldResultDomainModel(it) }
    }
}
