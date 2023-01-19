package cz.vvoleman.phr.api.backend

import cz.vvoleman.phr.data.diagnose.Diagnose

data class DiagnoseResponse(
    val results: List<Diagnose>
)
