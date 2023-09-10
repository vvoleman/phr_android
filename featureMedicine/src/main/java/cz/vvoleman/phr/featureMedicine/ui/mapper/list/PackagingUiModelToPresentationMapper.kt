package cz.vvoleman.phr.featureMedicine.ui.mapper.list

import cz.vvoleman.phr.featureMedicine.presentation.model.list.PackagingPresentationModel
import cz.vvoleman.phr.featureMedicine.ui.model.list.PackagingUiModel

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
