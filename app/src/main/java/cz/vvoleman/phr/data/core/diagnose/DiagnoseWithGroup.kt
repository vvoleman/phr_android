package cz.vvoleman.phr.data.core.diagnose

import android.os.Parcelable
import cz.vvoleman.phr.data.AdapterPair
import cz.vvoleman.phr.data.core.BaseData
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