package cz.vvoleman.phr.common.presentation.model.problemCategory

import java.time.LocalDateTime

data class ProblemCategoryPresentationModel(
    val id: String,
    val name: String,
    val color: String,
    val createdAt: LocalDateTime,
    val patientId: String
)
