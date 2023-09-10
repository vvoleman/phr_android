package cz.vvoleman.phr.featureMedicalRecord.domain.model.selectFile.result

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
data class DateFieldResultDomainModel(
    override val value: LocalDate
) : FieldResultDomainModel<LocalDate>(), Parcelable {

    override fun toString(): String {
        return value.toString()
    }
}
