package cz.vvoleman.phr.util.ocr.text_processor

abstract class FieldResult<T> {
    abstract val value: T

    abstract override fun toString(): String
}
