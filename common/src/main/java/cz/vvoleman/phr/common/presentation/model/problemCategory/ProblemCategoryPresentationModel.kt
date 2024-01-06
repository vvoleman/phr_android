package cz.vvoleman.phr.common.presentation.model.problemCategory

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class ProblemCategoryPresentationModel(
    val id: String,
    val name: String,
    val color: String,
    val createdAt: LocalDateTime,
    val patientId: String,
    val isDefault: Boolean
): Parcelable
