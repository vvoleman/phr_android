package cz.vvoleman.phr.featureMedicalRecord.domain.model.selectFile

sealed class GetTextFromInputImageResultDomainModel {
    data class Success(val text: TextDomainModel) : GetTextFromInputImageResultDomainModel()
    data class Failure(val exception: Exception) : GetTextFromInputImageResultDomainModel()
}
