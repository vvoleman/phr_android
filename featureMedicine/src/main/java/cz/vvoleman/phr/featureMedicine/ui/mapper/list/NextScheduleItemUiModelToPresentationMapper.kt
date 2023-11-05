package cz.vvoleman.phr.featureMedicine.ui.mapper.list

import cz.vvoleman.phr.featureMedicine.presentation.list.model.NextScheduleItemPresentationModel
import cz.vvoleman.phr.featureMedicine.ui.model.list.schedule.NextScheduleItemUiModel

class NextScheduleItemUiModelToPresentationMapper(
    private val mapper: ScheduleItemWithDetailsUiModelToPresentationMapper,
) {

    fun toPresentation(model: NextScheduleItemUiModel): NextScheduleItemPresentationModel {
        return NextScheduleItemPresentationModel(
            scheduleItems = model.scheduleItems.map { mapper.toPresentation(it) },
            dateTime = model.dateTime
        )
    }

    fun toUi(model: NextScheduleItemPresentationModel): NextScheduleItemUiModel {
        return NextScheduleItemUiModel(
            scheduleItems = model.scheduleItems.map { mapper.toUi(it) },
            dateTime = model.dateTime
        )
    }

}