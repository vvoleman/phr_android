package cz.vvoleman.phr.api.backend

import cz.vvoleman.phr.data.retrofit.diagnose.DiagnoseEntity


data class DiagnoseResponse(
    val data: List<DiagnoseEntity>
)
