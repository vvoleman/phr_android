package cz.vvoleman.phr.featureMedicine.domain.exception

import cz.vvoleman.phr.base.domain.exception.DomainException

class InvalidDateRangeException(throwable: Throwable) : DomainException(throwable) {
    constructor(errorMessage: String) : this(Throwable(errorMessage))
}
