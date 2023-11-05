package cz.vvoleman.phr.featureMedicine.domain.model.medicine

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SubstanceAmountDomainModel(
    val substance: SubstanceDomainModel,
    val amount: String,
    val unit: String
) : Parcelable
