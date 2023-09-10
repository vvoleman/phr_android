package cz.vvoleman.phr.feature_medicalrecord.presentation.addedit.exception

class MissingFieldsException(throwable: Throwable) : Exception(throwable) {
    override val message = "Missing fields"
}
