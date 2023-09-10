package cz.vvoleman.phr.featureMedicalRecord.domain.usecase.selectFile.ocr

import cz.vvoleman.phr.featureMedicalRecord.domain.model.selectFile.TextDomainModel

interface FieldProcessor<T> {

    suspend fun process(text: TextDomainModel): List<T>
}
