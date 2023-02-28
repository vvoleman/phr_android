package cz.vvoleman.phr.data.retrofit.diagnose

import cz.vvoleman.phr.data.core.diagnose.Diagnose
import cz.vvoleman.phr.data.core.diagnose.DiagnoseWithGroup

data class DiagnoseEntity(
    val id: String,
    val name: String,
    val parent: DiagnoseGroupEntity
) {

    fun toDiagnose(): Diagnose {
        return Diagnose(id, name)
    }

    fun toDiagnoseWithGroup(): DiagnoseWithGroup {
        return DiagnoseWithGroup(toDiagnose(), parent.toDiagnoseGroup())
    }

}
