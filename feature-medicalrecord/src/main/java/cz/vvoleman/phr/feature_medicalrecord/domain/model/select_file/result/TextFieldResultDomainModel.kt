package cz.vvoleman.phr.feature_medicalrecord.domain.model.select_file.result

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TextFieldResultDomainModel(
    override val value: String,
) : FieldResultDomainModel<String>(), Parcelable {

    override fun toString(): String {
        return value
    }

}
