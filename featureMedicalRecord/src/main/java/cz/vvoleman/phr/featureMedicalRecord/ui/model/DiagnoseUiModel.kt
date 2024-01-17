package cz.vvoleman.phr.featureMedicalRecord.ui.model

data class DiagnoseUiModel(
    val id: String,
    val name: String,
    val parent: DiagnoseGroupUiModel,
) {
    override fun toString(): String {
        return name
    }
}
