package cz.vvoleman.phr.featureMedicalRecord.data.datasource.model.retrofit.diagnose

data class DiagnoseDataSourceApiModel(
    val id: String,
    val name: String,
    val parent: DiagnoseGroupDataSourceApiModel
)
