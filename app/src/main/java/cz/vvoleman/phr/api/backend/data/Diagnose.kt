package cz.vvoleman.phr.api.backend.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import cz.vvoleman.phr.data.diagnose.Diagnose as RoomDiagnose
import cz.vvoleman.phr.data.diagnose.DiagnoseGroup as RoomDiagnoseGroup

@Parcelize
data class Diagnose(
    var id: String,
    var name: String,
    var parent: DiagnoseGroup
) : Parcelable {

    @Parcelize
    data class DiagnoseGroup(
        var id: String,
        var name: String,
    ) : Parcelable {
        fun getEntity(): RoomDiagnoseGroup {
            return RoomDiagnoseGroup(id, name)
        }
    }

    fun getEntity(): RoomDiagnose{
        return RoomDiagnose(id, name, parent.id)
    }

}
