package cz.vvoleman.phr.feature_medicalrecord.domain.model.select_file

import android.os.Parcelable
import cz.vvoleman.phr.feature_medicalrecord.domain.model.select_file.result.DateFieldResultDomainModel
import cz.vvoleman.phr.feature_medicalrecord.domain.model.select_file.result.DiagnoseFieldResultDomainModel
import cz.vvoleman.phr.feature_medicalrecord.domain.model.select_file.result.PatientFieldResultDomainModel
import cz.vvoleman.phr.feature_medicalrecord.domain.model.select_file.result.TextFieldResultDomainModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class RecognizedOptionsDomainModel(
    var visitDate: List<DateFieldResultDomainModel> = emptyList(),
    var diagnose: List<DiagnoseFieldResultDomainModel> = emptyList(),
    var patient: List<PatientFieldResultDomainModel> = emptyList(),
): Parcelable