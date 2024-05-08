package cz.vvoleman.phr.common.domain.model.problemCategory.request

import java.time.LocalDateTime

data class SaveProblemCategoryRequest(
    val id: String? = null,
    val patientId: String,
    val name: String,
    val color: String,
    val createdAt: LocalDateTime?,
    val isDefault: Boolean = false,
)
