package cz.vvoleman.phr.featureMedicalRecord.ui.mapper

import cz.vvoleman.phr.common.presentation.model.problemCategory.ProblemCategoryPresentationModel
import cz.vvoleman.phr.common.ui.model.problemCategory.ColorUiModel

class ProblemCategoryUiModelToColorMapper {

    fun toColor(model: ProblemCategoryPresentationModel): ColorUiModel {
        return ColorUiModel(
            name = model.name,
            color = model.color
        )
    }

    fun toColor(model: List<ProblemCategoryPresentationModel>): List<ColorUiModel> {
        return model.map { toColor(it) }
    }

}
