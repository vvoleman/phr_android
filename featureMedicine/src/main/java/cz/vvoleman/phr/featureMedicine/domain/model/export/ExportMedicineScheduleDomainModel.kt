package cz.vvoleman.phr.featureMedicine.domain.model.export

import cz.vvoleman.phr.common.domain.model.patient.PatientDomainModel
import cz.vvoleman.phr.featureMedicine.domain.model.medicine.MedicineDomainModel

data class ExportMedicineScheduleDomainModel(
    val id: String,
    val medicine: MedicineDomainModel,
    val patient: PatientDomainModel,
    val schedules: List<ExportScheduleItemDomainModel>,
)
