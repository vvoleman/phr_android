package cz.vvoleman.phr.common.presentation.model.problemCategory.detail

sealed class DetailProblemCategoryNotification {
    data class ExportPdf(val params: ExportDetailProblemCategoryParams) : DetailProblemCategoryNotification()
}
