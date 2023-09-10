package cz.vvoleman.phr.featureMedicalRecord.domain.model.selectFile

import android.os.Parcelable
import cz.vvoleman.phr.featureMedicalRecord.domain.model.selectFile.result.DateFieldResultDomainModel
import cz.vvoleman.phr.featureMedicalRecord.domain.model.selectFile.result.DiagnoseFieldResultDomainModel
import cz.vvoleman.phr.featureMedicalRecord.domain.model.selectFile.result.PatientFieldResultDomainModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class RecognizedOptionsDomainModel(
    var visitDate: List<DateFieldResultDomainModel> = emptyList(),
    var diagnose: List<DiagnoseFieldResultDomainModel> = emptyList(),
    var patient: List<PatientFieldResultDomainModel> = emptyList()
) : Parcelable
