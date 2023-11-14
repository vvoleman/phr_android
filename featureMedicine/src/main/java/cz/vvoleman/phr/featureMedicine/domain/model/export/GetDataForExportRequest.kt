package cz.vvoleman.phr.featureMedicine.domain.model.export

import cz.vvoleman.phr.featureMedicine.domain.model.medicine.MedicineDomainModel
import java.time.LocalDateTime

data class GetDataForExportRequest(
    val patientId: String,
    val currentDateTime: LocalDateTime,
    val medicine: List<MedicineDomainModel>,
    val dateRange: Pair<LocalDateTime?, LocalDateTime?>
)
