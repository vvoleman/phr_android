package cz.vvoleman.phr.common.presentation.model.problemCategory.list

import cz.vvoleman.phr.common.presentation.model.patient.PatientPresentationModel
import cz.vvoleman.phr.common.presentation.model.problemCategory.ProblemCategoryPresentationModel
import cz.vvoleman.phr.common.presentation.model.problemCategory.ProblemCategoryWithInfoPresentationModel

data class ListProblemCategoryViewState(
    val patient: PatientPresentationModel,
    val problemCategories: List<ProblemCategoryWithInfoPresentationModel>? = null
)
