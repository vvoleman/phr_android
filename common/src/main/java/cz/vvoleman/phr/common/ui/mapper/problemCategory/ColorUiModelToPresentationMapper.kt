package cz.vvoleman.phr.common.ui.mapper.problemCategory

import cz.vvoleman.phr.common.presentation.model.problemCategory.ColorPresentationModel
import cz.vvoleman.phr.common.ui.model.problemCategory.ColorUiModel

class ColorUiModelToPresentationMapper {

    fun toPresentation(model: ColorUiModel): ColorPresentationModel {
        return ColorPresentationModel(
            name = model.name,
            color = model.color
        )
    }

    fun toUi(model: ColorPresentationModel): ColorUiModel {
        return ColorUiModel(
            name = model.name,
            color = model.color
        )
    }
}
