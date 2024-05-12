package cz.vvoleman.phr.common.ui.mapper.problemCategory

import cz.vvoleman.phr.common.presentation.model.healthcare.core.AdditionalInfoPresentationModel
import cz.vvoleman.phr.common.presentation.model.problemCategory.ProblemCategoryInfoPresentationModel
import cz.vvoleman.phr.common.ui.model.healthcare.core.AdditionalInfoUiModel
import cz.vvoleman.phr.common.ui.model.problemCategory.ProblemCategoryInfoUiModel

class ProblemCategoryInfoUiModelToPresentationMapper(
    private val problemCategoryUiModelToPresentationMapper: ProblemCategoryUiModelToPresentationMapper
) {

    fun toPresentation(model: ProblemCategoryInfoUiModel): ProblemCategoryInfoPresentationModel {
        return ProblemCategoryInfoPresentationModel(
            mainSlot = model.mainSlot,
            secondarySlots = model.secondarySlots.map {
                AdditionalInfoPresentationModel(
                    icon = it.icon,
                    text = it.text,
                    onClick = it.onClick?.let { onClick ->
                        {
                                problemCategory ->
                            onClick(
                                problemCategoryUiModelToPresentationMapper.toUi(
                                    problemCategory
                                )
                            )
                        }
                    }
                )
            }
        )
    }

    fun toUi(model: ProblemCategoryInfoPresentationModel): ProblemCategoryInfoUiModel {
        return ProblemCategoryInfoUiModel(
            mainSlot = model.mainSlot,
            secondarySlots = model.secondarySlots.map {
                AdditionalInfoUiModel(
                    icon = it.icon,
                    text = it.text,
                    onClick = it.onClick?.let { onClick ->
                        {
                                problemCategory ->
                            onClick(
                                problemCategoryUiModelToPresentationMapper.toPresentation(
                                    problemCategory
                                )
                            )
                        }
                    }
                )
            }
        )
    }
}
