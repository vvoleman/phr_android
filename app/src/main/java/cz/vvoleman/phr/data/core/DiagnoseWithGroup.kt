package cz.vvoleman.phr.data.core

import android.os.Parcelable
import cz.vvoleman.phr.data.AdapterPair
import kotlinx.parcelize.Parcelize

@Parcelize
data class DiagnoseWithGroup(
    val diagnose: Diagnose,
    val diagnoseGroup: DiagnoseGroup
) : Parcelable, BaseData() {
    override fun getAdapterPair(): AdapterPair {
        return AdapterPair(diagnose.id, diagnose.name, this)
    }
}