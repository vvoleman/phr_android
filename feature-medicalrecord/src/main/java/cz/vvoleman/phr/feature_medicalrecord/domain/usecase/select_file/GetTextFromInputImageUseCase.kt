package cz.vvoleman.phr.feature_medicalrecord.domain.usecase.select_file

import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import cz.vvoleman.phr.base.domain.usecase.UseCase
import cz.vvoleman.phr.feature_medicalrecord.domain.mapper.TextToTextDomainModelMapper
import cz.vvoleman.phr.feature_medicalrecord.domain.model.select_file.GetTextFromInputImageResultDomainModel

class GetTextFromInputImageUseCase(
    private val textToTextDomainModelMapper: TextToTextDomainModelMapper
) : UseCase<InputImage, GetTextFromInputImageResultDomainModel> {

    override suspend fun execute(input: InputImage, onResult: (GetTextFromInputImageResultDomainModel) -> Unit) {
        val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
        recognizer.process(input).addOnSuccessListener { text ->
            val textModel = textToTextDomainModelMapper.toDomain(text)
            onResult(GetTextFromInputImageResultDomainModel.Success(textModel))
        }.addOnFailureListener { e ->
            onResult(GetTextFromInputImageResultDomainModel.Failure(e))
        }
    }
}