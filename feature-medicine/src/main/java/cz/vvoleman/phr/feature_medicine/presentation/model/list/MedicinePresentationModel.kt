package cz.vvoleman.phr.feature_medicine.presentation.model.list

data class MedicinePresentationModel(
    val id: String,
    val name: String,
    val packaging: PackagingPresentationModel,
    val country: String,
    val substances: List<SubstanceAmountPresentationModel>,
)
