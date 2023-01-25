package cz.vvoleman.phr.ui.medical_records

data class FilterPair(
    val id: String,
    val stringValue: String,
    val objectValue: Any,
    var checked: Boolean = false,
)
