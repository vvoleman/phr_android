package cz.vvoleman.phr.featureMedicine.domain.model.medicine

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MedicineDomainModel(
    val id: String,
    val name: String,
    val packaging: PackagingDomainModel,
    val country: String,
    val substances: List<SubstanceAmountDomainModel>
): Parcelable
