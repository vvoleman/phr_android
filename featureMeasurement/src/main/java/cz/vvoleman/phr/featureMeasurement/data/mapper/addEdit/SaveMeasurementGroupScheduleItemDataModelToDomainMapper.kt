package cz.vvoleman.phr.featureMeasurement.data.mapper.addEdit

import cz.vvoleman.phr.featureMeasurement.data.model.addEdit.SaveMeasurementGroupScheduleItemDataModel
import cz.vvoleman.phr.featureMeasurement.domain.model.addEdit.SaveMeasurementGroupScheduleItemDomainModel

class SaveMeasurementGroupScheduleItemDataModelToDomainMapper {

    fun toData(model: SaveMeasurementGroupScheduleItemDomainModel): SaveMeasurementGroupScheduleItemDataModel {
        return SaveMeasurementGroupScheduleItemDataModel(
            id = model.id,
            dayOfWeek = model.dayOfWeek,
            time = model.time,
            scheduledAt = model.scheduledAt,
        )
    }

    fun toData(models: List<SaveMeasurementGroupScheduleItemDomainModel>): List<SaveMeasurementGroupScheduleItemDataModel> {
        return models.map { toData(it) }
    }
}
