package cz.vvoleman.phr.common.ui.component.nextSchedule

import cz.vvoleman.phr.common.presentation.model.nextSchedule.NextSchedulePresentationModel

class NextScheduleUiModelToPresentationMapper(
    private val itemMapper: NextScheduleItemUiModelToPresentationMapper,
) {

    fun toPresentation(model: NextScheduleUiModel): NextSchedulePresentationModel {
        return NextSchedulePresentationModel(
            dateTime = model.dateTime,
            items = model.items.map { itemMapper.toPresentation(it) }
        )
    }

    fun toPresentation(models: List<NextScheduleUiModel>): List<NextSchedulePresentationModel> {
        return models.map { toPresentation(it) }
    }

    fun toUi(model: NextSchedulePresentationModel): NextScheduleUiModel {
        return NextScheduleUiModel(
            dateTime = model.dateTime,
            items = model.items.map { itemMapper.toUi(it) }
        )
    }

    fun toUi(models: List<NextSchedulePresentationModel>): List<NextScheduleUiModel> {
        return models.map { toUi(it) }
    }

}
