package cz.vvoleman.phr.featureMedicalRecord.ui.model

data class DiagnoseItemUiModel(
    val id: String,
    val name: String
) {
    override fun toString(): String {
        return name
    }
}
