package cz.vvoleman.phr.feature_medicalrecord.domain.usecase.select_file.ocr

import cz.vvoleman.phr.feature_medicalrecord.domain.model.select_file.TextDomainModel

interface FieldProcessor<T> {

    suspend fun process(text: TextDomainModel): List<T>

}