package cz.vvoleman.phr.feature_medicalrecord.data.datasource.model.retrofit.diagnose

data class DiagnoseDataSourceApiModel(
    val id: String,
    val name: String,
    val parent: DiagnoseGroupDataSourceApiModel
)
