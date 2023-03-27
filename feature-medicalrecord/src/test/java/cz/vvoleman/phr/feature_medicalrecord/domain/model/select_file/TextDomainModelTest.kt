package cz.vvoleman.phr.feature_medicalrecord.domain.model.select_file

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class TextDomainModelTest {

    @Test
    fun `Test recursive text`() {
        // Preparation
        val textModel = getTextDomainModel()

        // Execution
        val actualText = textModel.text.getText()

        // Verification
        assertEquals(textModel.expectedText, actualText)
    }

    private fun getTextDomainModel(): TestData {
        val textA = "ahoj"
        val textB = "svete"

        val a = textToTextModels(textA)
        val b = textToTextModels(textB)

        val lineA = TextDomainModel(
            texts = a
        )

        val lineB = TextDomainModel(
            texts = b
        )

        val first = TextDomainModel(
            texts = listOf(lineA, lineB)
        )

        return TestData(
            text = first,
            expectedText = textA+textB
        )
    }

    private fun textToTextModels(text: String): List<TextDomainModel> {
        val list = mutableListOf<TextDomainModel>()

        for ((i, c) in text.withIndex()) {
            list.add(TextDomainModel(
                texts = emptyList(),
                value = c.toString()
            ))
        }

        return list
    }

    data class TestData(
        val text: TextDomainModel,
        val expectedText: String
    )

}