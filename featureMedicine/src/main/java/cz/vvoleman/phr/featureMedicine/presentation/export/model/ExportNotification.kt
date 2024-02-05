package cz.vvoleman.phr.featureMedicine.presentation.export.model

import cz.vvoleman.phr.common.domain.model.export.ExportType


sealed class ExportNotification {
    object CannotExport : ExportNotification()
    object CannotLoadData : ExportNotification()
    data class ExportAs(val type: ExportType, val params: ExportParamsPresentationModel) : ExportNotification()
}
