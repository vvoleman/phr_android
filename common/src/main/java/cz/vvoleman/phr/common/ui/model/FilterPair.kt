package cz.vvoleman.phr.common.ui.model

data class FilterPair(
    val id: String,
    val stringValue: String,
    val objectValue: Any,
    var checked: Boolean = false
)
