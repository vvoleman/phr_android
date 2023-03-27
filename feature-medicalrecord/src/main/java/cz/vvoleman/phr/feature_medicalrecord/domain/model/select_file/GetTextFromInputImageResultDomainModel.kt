package cz.vvoleman.phr.feature_medicalrecord.domain.model.select_file

sealed class GetTextFromInputImageResultDomainModel {
    data class Success(val text: TextDomainModel) : GetTextFromInputImageResultDomainModel()
    data class Failure(val exception: Exception) : GetTextFromInputImageResultDomainModel()
}
