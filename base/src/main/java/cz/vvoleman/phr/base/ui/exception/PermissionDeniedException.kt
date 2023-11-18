package cz.vvoleman.phr.base.ui.exception

class PermissionDeniedException(throwable: Throwable) : UiException(throwable) {
    constructor(message: String) : this(Throwable(message))
}
