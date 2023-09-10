package cz.vvoleman.phr.featureMedicalRecord.domain.model.selectFile.result

import android.os.Parcelable
import cz.vvoleman.phr.featureMedicalRecord.domain.model.DiagnoseDomainModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class DiagnoseFieldResultDomainModel(
    override val value: DiagnoseDomainModel
) : FieldResultDomainModel<DiagnoseDomainModel>(), Parcelable {
    override fun toString(): String {
        return value.id
    }
}
