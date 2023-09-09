package cz.vvoleman.phr.feature_medicalrecord.ui.model

data class DiagnoseItemUiModel(
    val id: String,
    val name: String
) {
    override fun toString(): String {
        return name
    }
}
