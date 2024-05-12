package cz.vvoleman.phr.featureMeasurement.presentation.mapper.list

import cz.vvoleman.phr.common.presentation.model.nextSchedule.NextScheduleItemPresentationModel
import cz.vvoleman.phr.common.presentation.model.nextSchedule.NextSchedulePresentationModel
import cz.vvoleman.phr.featureMeasurement.presentation.model.core.ScheduledMeasurementGroupPresentationModel

class MeasurementGroupPresentationModelToNextScheduleMapper {

    fun toNextSchedule(model: List<ScheduledMeasurementGroupPresentationModel>): NextSchedulePresentationModel {
        val modelTime = model.first().dateTime
        return NextSchedulePresentationModel(
            dateTime = modelTime,
            items = model.map {
                NextScheduleItemPresentationModel(
                    id = it.measurementGroup.id,
                    time = modelTime,
                    name = it.measurementGroup.name,
                )
            }
        )
    }
}
