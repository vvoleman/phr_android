package cz.vvoleman.phr.common.presentation.model.problemCategory.detail

import cz.vvoleman.phr.common.presentation.model.problemCategory.export.ProblemCategoryParams
import cz.vvoleman.phr.common.ui.export.usecase.DocumentPage

sealed class DetailProblemCategoryNotification {
    data class ExportPdf(
        val params: ProblemCategoryParams,
        val pages: List<DocumentPage>
    ) : DetailProblemCategoryNotification()

    object ExportEmpty : DetailProblemCategoryNotification()
}
