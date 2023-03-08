package cz.vvoleman.phr.base.domain.exception

import cz.vvoleman.phr.base.domain.exception.DomainException

class UnknownDomainException(throwable: Throwable) : DomainException(throwable) {
    constructor(errorMessage: String) : this(Throwable(errorMessage))
}
