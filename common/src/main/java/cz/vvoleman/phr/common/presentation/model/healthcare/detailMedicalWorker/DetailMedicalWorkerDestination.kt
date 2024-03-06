package cz.vvoleman.phr.common.presentation.model.healthcare.detailMedicalWorker

import cz.vvoleman.phr.base.presentation.model.PresentationDestination

sealed class DetailMedicalWorkerDestination : PresentationDestination {
    data class Edit(val medicalWorkerId: String) : DetailMedicalWorkerDestination()
}
