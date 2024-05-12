package cz.vvoleman.phr.common.ui.component.nextSchedule

import cz.vvoleman.phr.common.presentation.model.nextSchedule.NextScheduleItemPresentationModel

class NextScheduleItemUiModelToPresentationMapper {

    fun toPresentation(model: NextScheduleItemUiModel): NextScheduleItemPresentationModel {
        return NextScheduleItemPresentationModel(
            id = model.id,
            time = model.time,
            name = model.name,
            additionalInfo = model.additionalInfo
        )
    }

    fun toPresentation(models: List<NextScheduleItemUiModel>): List<NextScheduleItemPresentationModel> {
        return models.map { toPresentation(it) }
    }

    fun toUi(model: NextScheduleItemPresentationModel): NextScheduleItemUiModel {
        return NextScheduleItemUiModel(
            id = model.id,
            time = model.time,
            name = model.name,
            additionalInfo = model.additionalInfo
        )
    }

    fun toUi(models: List<NextScheduleItemPresentationModel>): List<NextScheduleItemUiModel> {
        return models.map { toUi(it) }
    }
}
