package cz.vvoleman.phr.common.presentation.model.problemCategory.detail

import cz.vvoleman.phr.common.presentation.model.patient.PatientPresentationModel
import cz.vvoleman.phr.common.presentation.model.problemCategory.ProblemCategoryPresentationModel

data class ExportDetailProblemCategoryParams(
    val problemCategory: ProblemCategoryPresentationModel,
    val patient: PatientPresentationModel
)
