package cz.vvoleman.phr.featureMedicalRecord.presentation.addEdit.exception

class MissingFieldsException(throwable: Throwable) : Exception(throwable) {
    override val message = "Missing fields"
}
