package cz.vvoleman.phr.featureMedicalRecord.presentation.model.detail

import cz.vvoleman.phr.base.presentation.model.PresentationDestination

sealed class DetailMedicalRecordDestination: PresentationDestination {
    data class NoMedicalRecord(val id: String?) : DetailMedicalRecordDestination()
}
