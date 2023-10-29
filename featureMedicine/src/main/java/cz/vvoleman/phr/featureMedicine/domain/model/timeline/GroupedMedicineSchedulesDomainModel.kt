package cz.vvoleman.phr.featureMedicine.domain.model.timeline

import cz.vvoleman.phr.featureMedicine.domain.model.schedule.MedicineScheduleDomainModel

data class GroupedMedicineSchedulesDomainModel (
    val groupLabel: String,
    val schedules: List<MedicineScheduleDomainModel>
)