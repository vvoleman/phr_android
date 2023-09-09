package cz.vvoleman.phr.feature_medicalrecord.domain.model.select_file.result

abstract class FieldResultDomainModel<T> {
    abstract val value: T

    abstract override fun toString(): String
}
