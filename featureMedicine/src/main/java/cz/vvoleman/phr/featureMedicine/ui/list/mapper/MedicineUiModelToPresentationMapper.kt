package cz.vvoleman.phr.featureMedicine.ui.list.mapper

import cz.vvoleman.phr.featureMedicine.presentation.list.model.MedicinePresentationModel
import cz.vvoleman.phr.featureMedicine.ui.list.model.MedicineUiModel

class MedicineUiModelToPresentationMapper(
    private val packagingMapper: PackagingUiModelToPresentationMapper,
    private val substanceAmountMapper: SubstanceAmountUiModelToPresentationMapper
) {
    fun toPresentation(presentation: MedicineUiModel): MedicinePresentationModel = MedicinePresentationModel(
        id = presentation.id,
        name = presentation.name,
        packaging = packagingMapper.toPresentation(presentation.packaging),
        country = presentation.country,
        substances = presentation.substances.map { substanceAmountMapper.toPresentation(it) }
    )

    fun toUi(domain: MedicinePresentationModel): MedicineUiModel = MedicineUiModel(
        id = domain.id,
        name = domain.name,
        packaging = packagingMapper.toUi(domain.packaging),
        country = domain.country,
        substances = domain.substances.map { substanceAmountMapper.toUi(it) }
    )
}
