package cz.vvoleman.phr.common.presentation.model.problemCategory.export

import cz.vvoleman.phr.common.presentation.model.patient.PatientPresentationModel
import cz.vvoleman.phr.common.presentation.model.problemCategory.ProblemCategoryPresentationModel

data class ProblemCategoryParams(
    val problemCategory: ProblemCategoryPresentationModel,
    val patient: PatientPresentationModel,
)
