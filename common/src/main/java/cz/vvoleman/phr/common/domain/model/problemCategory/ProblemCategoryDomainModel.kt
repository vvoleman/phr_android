package cz.vvoleman.phr.common.domain.model.problemCategory

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class ProblemCategoryDomainModel(
    val id: String,
    val name: String,
    val color: String,
    val createdAt: LocalDateTime,
    val patientId: String
) : Parcelable
