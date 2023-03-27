package cz.vvoleman.phr.api.backend

import cz.vvoleman.phr.feature_medicalrecord.data.datasource.model.retrofit.diagnose.DiagnoseDataSourceApiModel


data class DiagnoseResponse(
    val data: List<DiagnoseDataSourceApiModel>
)
