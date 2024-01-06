package cz.vvoleman.phr.common.ui.model.problemCategory

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class ProblemCategoryUiModel(
    val id: String,
    val name: String,
    val color: String,
    val createdAt: LocalDateTime,
    val patientId: String,
    val isDefault: Boolean
) : Parcelable
