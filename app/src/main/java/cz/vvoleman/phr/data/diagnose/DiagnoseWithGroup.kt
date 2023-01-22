package cz.vvoleman.phr.data.diagnose

import androidx.room.Embedded
import androidx.room.Relation
import cz.vvoleman.phr.data.AdapterPair

data class DiagnoseWithGroup(
    @Embedded val diagnose: Diagnose,
    @Relation(
        parentColumn = "parent",
        entityColumn = "id"
    )
    val diagnoseGroup: DiagnoseGroup
) {
    override fun toString(): String {
        return diagnose.name
    }

    fun getAdapterPair(): AdapterPair {
        return AdapterPair(diagnose.id, diagnose.name, this)
    }
}
