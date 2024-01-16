package cz.vvoleman.phr.common.ui.model.problemCategory

data class ColorUiModel(
    val name: String,
    val color: String
) {
    override fun toString(): String {
        return name
    }
}
