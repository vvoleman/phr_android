package cz.vvoleman.phr.featureMeasurement.presentation.model.addEditEntry

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class NavigationSource : Parcelable {
    object List : NavigationSource()
    data class Detail(val measurementGroupId: String, val name: String) : NavigationSource()
}
