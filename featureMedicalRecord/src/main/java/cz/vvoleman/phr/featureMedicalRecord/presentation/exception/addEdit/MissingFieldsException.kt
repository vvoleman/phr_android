package cz.vvoleman.phr.featureMedicalRecord.presentation.exception.addEdit

class MissingFieldsException(throwable: Throwable) : Exception(throwable) {
    override val message = "Missing fields"
}
