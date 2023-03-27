package cz.vvoleman.phr.feature_medicalrecord.domain.model.select_file.result

import android.os.Parcelable
import cz.vvoleman.phr.feature_medicalrecord.domain.model.DiagnoseDomainModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class DiagnoseFieldResultDomainModel(
    override val value: DiagnoseDomainModel
) : FieldResultDomainModel<DiagnoseDomainModel>(), Parcelable {
    override fun toString(): String {
        return value.id
    }
}
