package cz.vvoleman.phr.featureMedicalRecord.domain.model.selectFile.result

import android.os.Parcelable
import cz.vvoleman.phr.common.domain.model.patient.PatientDomainModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class PatientFieldResultDomainModel(
    override val value: PatientDomainModel
) : FieldResultDomainModel<PatientDomainModel>(), Parcelable {

    override fun toString(): String {
        return value.toString()
    }
}
