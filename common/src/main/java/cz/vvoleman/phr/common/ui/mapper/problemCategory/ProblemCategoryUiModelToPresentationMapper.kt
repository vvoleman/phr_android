package cz.vvoleman.phr.common.ui.mapper.problemCategory

import cz.vvoleman.phr.common.presentation.model.problemCategory.ProblemCategoryPresentationModel
import cz.vvoleman.phr.common.ui.model.problemCategory.ProblemCategoryUiModel

class ProblemCategoryUiModelToPresentationMapper {

    fun toPresentation(model: ProblemCategoryUiModel): ProblemCategoryPresentationModel {
        return ProblemCategoryPresentationModel(
            id = model.id,
            name = model.name,
            color = model.color,
            createdAt = model.createdAt,
            patientId = model.patientId
        )
    }

    fun toUi(model: ProblemCategoryPresentationModel): ProblemCategoryUiModel {
        return ProblemCategoryUiModel(
            id = model.id,
            name = model.name,
            color = model.color,
            createdAt = model.createdAt,
            patientId = model.patientId
        )
    }
}
