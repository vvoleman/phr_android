package cz.vvoleman.phr.featureMedicalRecord.domain.model.selectFile.result

abstract class FieldResultDomainModel<T> {
    abstract val value: T

    abstract override fun toString(): String
}
