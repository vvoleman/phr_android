package cz.vvoleman.phr.common.ui.mapper.problemCategory

import cz.vvoleman.phr.common.presentation.model.problemCategory.ProblemCategoryWithInfoPresentationModel
import cz.vvoleman.phr.common.ui.model.problemCategory.ProblemCategoryWithInfoUiModel

class ProblemCategoryWithInfoUiModelToPresentationMapper(
    private val problemCategoryUiModelToPresentationMapper: ProblemCategoryUiModelToPresentationMapper,
    private val problemCategoryInfoUiModelToPresentationMapper: ProblemCategoryInfoUiModelToPresentationMapper
) {

    fun toPresentation(model: ProblemCategoryWithInfoUiModel): ProblemCategoryWithInfoPresentationModel {
        return ProblemCategoryWithInfoPresentationModel(
            problemCategory = problemCategoryUiModelToPresentationMapper.toPresentation(model.problemCategory),
            info = problemCategoryInfoUiModelToPresentationMapper.toPresentation(model.info)
        )
    }

    fun toUi(model: ProblemCategoryWithInfoPresentationModel): ProblemCategoryWithInfoUiModel {
        return ProblemCategoryWithInfoUiModel(
            problemCategory = problemCategoryUiModelToPresentationMapper.toUi(model.problemCategory),
            info = problemCategoryInfoUiModelToPresentationMapper.toUi(model.info)
        )
    }

}
