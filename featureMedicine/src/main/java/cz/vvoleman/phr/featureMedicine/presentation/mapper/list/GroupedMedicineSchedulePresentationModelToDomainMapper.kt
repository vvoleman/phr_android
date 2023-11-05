package cz.vvoleman.phr.featureMedicine.presentation.mapper.list

import cz.vvoleman.phr.featureMedicine.domain.model.timeline.GroupedMedicineSchedulesDomainModel
import cz.vvoleman.phr.featureMedicine.presentation.model.list.GroupedMedicineSchedulesPresentationModel

class GroupedMedicineSchedulePresentationModelToDomainMapper(
    private val mapper: MedicineSchedulePresentationModelToDomainMapper,
) {

    fun toDomain(model: GroupedMedicineSchedulesPresentationModel): GroupedMedicineSchedulesDomainModel {
        return GroupedMedicineSchedulesDomainModel(
            groupLabel = model.groupLabel,
            schedules = model.schedules.map {
                mapper.toDomain(it)
            }
        )
    }

    fun toPresentation(model: GroupedMedicineSchedulesDomainModel): GroupedMedicineSchedulesPresentationModel {
        return GroupedMedicineSchedulesPresentationModel(
            groupLabel = model.groupLabel,
            schedules = model.schedules.map {
                mapper.toPresentation(it)
            }
        )
    }

}