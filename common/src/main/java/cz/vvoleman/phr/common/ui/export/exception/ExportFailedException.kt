package cz.vvoleman.phr.common.ui.export.exception

import cz.vvoleman.phr.base.ui.exception.UiException
import cz.vvoleman.phr.common.domain.model.export.ExportType

class ExportFailedException(throwable: Throwable) : UiException(throwable) {
    constructor(exportType: ExportType) : this(Throwable("Export of type $exportType failed"))
}
