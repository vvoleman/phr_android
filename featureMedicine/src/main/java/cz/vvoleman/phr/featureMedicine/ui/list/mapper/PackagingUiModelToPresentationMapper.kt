package cz.vvoleman.phr.featureMedicine.ui.list.mapper

import cz.vvoleman.phr.featureMedicine.presentation.list.model.PackagingPresentationModel
import cz.vvoleman.phr.featureMedicine.ui.list.model.PackagingUiModel

class PackagingUiModelToPresentationMapper(
    private val formMapper: ProductFormUiModelToPresentationMapper
) {
    fun toPresentation(presentation: PackagingUiModel): PackagingPresentationModel =
        PackagingPresentationModel(
            form = formMapper.toPresentation(presentation.form),
            packaging = presentation.packaging
        )

    fun toUi(domain: PackagingPresentationModel): PackagingUiModel =
        PackagingUiModel(
            form = formMapper.toUi(domain.form),
            packaging = domain.packaging
        )
}
