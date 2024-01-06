package cz.vvoleman.phr.common.presentation.model.problemCategory.addEdit

import cz.vvoleman.phr.common.presentation.model.patient.PatientPresentationModel
import cz.vvoleman.phr.common.presentation.model.problemCategory.ColorPresentationModel
import java.time.LocalDateTime

data class AddEditProblemCategoryViewState(
    val problemCategoryId: String? = null,
    val patient: PatientPresentationModel,
    val name: String? = null,
    val colorList: List<ColorPresentationModel>? = null,
    val selectedColor: String? = null,
    val createdAt: LocalDateTime? = null,

) {
    val missingFields: List<RequiredField>
        get() {
            val missingFields = mutableListOf<RequiredField>()
            if (name.isNullOrBlank()) {
                missingFields.add(RequiredField.NAME)
            }
            if (selectedColor.isNullOrBlank()) {
                missingFields.add(RequiredField.COLOR)
            }

            return missingFields.toList()
        }

    enum class RequiredField {
        NAME,
        COLOR
    }
}
