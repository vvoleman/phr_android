package cz.vvoleman.phr.featureMedicine.presentation.model.list

data class MedicinePresentationModel(
    val id: String,
    val name: String,
    val packaging: PackagingPresentationModel,
    val country: String,
    val substances: List<SubstanceAmountPresentationModel>
)
