package cz.vvoleman.phr.data.retrofit.diagnose

import cz.vvoleman.phr.data.core.DiagnoseGroup

data class DiagnoseGroupEntity(
    val id: String,
    val name: String
) {

    fun toDiagnoseGroup(): DiagnoseGroup {
        return DiagnoseGroup(id, name)
    }

}