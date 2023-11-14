package cz.vvoleman.phr.featureMedicine.presentation.export.model

sealed class ExportNotification {
    object CannotExport : ExportNotification()
}
