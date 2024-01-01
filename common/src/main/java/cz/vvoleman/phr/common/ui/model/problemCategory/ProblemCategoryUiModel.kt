package cz.vvoleman.phr.common.ui.model.problemCategory

import java.time.LocalDateTime

data class ProblemCategoryUiModel(
    val id: String,
    val name: String,
    val color: String,
    val createdAt: LocalDateTime,
    val patientId: String
)
