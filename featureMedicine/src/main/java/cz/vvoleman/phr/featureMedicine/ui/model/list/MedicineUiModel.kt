package cz.vvoleman.phr.featureMedicine.ui.model.list

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MedicineUiModel(
    val id: String,
    val name: String,
    val packaging: PackagingUiModel,
    val country: String,
    val substances: List<SubstanceAmountUiModel>
) : Parcelable
