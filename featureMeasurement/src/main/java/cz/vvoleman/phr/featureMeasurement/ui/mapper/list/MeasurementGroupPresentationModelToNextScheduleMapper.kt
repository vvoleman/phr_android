package cz.vvoleman.phr.featureMeasurement.ui.mapper.list

import cz.vvoleman.phr.common.ui.component.nextSchedule.NextScheduleItemUiModel
import cz.vvoleman.phr.common.ui.component.nextSchedule.NextScheduleUiModel
import cz.vvoleman.phr.featureMeasurement.domain.facade.MeasurementTranslateDateTimeFacade
import cz.vvoleman.phr.featureMeasurement.presentation.mapper.core.MeasurementGroupScheduleItemPresentationModelToDomainMapper
import cz.vvoleman.phr.featureMeasurement.presentation.model.core.MeasurementGroupPresentationModel
import cz.vvoleman.phr.featureMeasurement.presentation.model.core.MeasurementGroupScheduleItemPresentationModel
import java.time.LocalDateTime

class MeasurementGroupPresentationModelToNextScheduleMapper(
    private val translateFacade: MeasurementTranslateDateTimeFacade,
    private val domainMapper: MeasurementGroupScheduleItemPresentationModelToDomainMapper,
    ) {

    fun toNextSchedule(model: MeasurementGroupPresentationModel): NextScheduleUiModel {
        val now = LocalDateTime.now()

        return NextScheduleUiModel(
            id = model.id,
            items = model.scheduleItems.map {
                NextScheduleItemUiModel(
                    id = it.id,
                    name = model.name,
                    time = getTime(it, now)
                )
            },
        )
    }

    private fun getTime(item: MeasurementGroupScheduleItemPresentationModel, time: LocalDateTime): LocalDateTime {
        val model = domainMapper.toDomain(item)

        return translateFacade.getLocalTime(model, time)
    }

}
