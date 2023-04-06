package cz.vvoleman.phr.feature_medicine.domain.model.schedule

data class ScheduledByMedicineRequestDomainModel(
    val medicineId: String,
    val patientId: String,
)
