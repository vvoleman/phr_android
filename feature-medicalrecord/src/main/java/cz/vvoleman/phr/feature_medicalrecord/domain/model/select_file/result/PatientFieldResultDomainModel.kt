package cz.vvoleman.phr.feature_medicalrecord.domain.model.select_file.result

import android.os.Parcelable
import cz.vvoleman.phr.feature_medicalrecord.domain.model.PatientDomainModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class PatientFieldResultDomainModel(
    override val value: PatientDomainModel
) : FieldResultDomainModel<PatientDomainModel>(), Parcelable {

    override fun toString(): String {
        return value.toString()
    }
}
