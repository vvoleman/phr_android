package cz.vvoleman.phr.data

data class AdapterPair(
    val id: String,
    val stringValue: String,
    val objectValue: Any
) {
    override fun toString(): String {
        return stringValue
    }
}
