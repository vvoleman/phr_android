package cz.vvoleman.phr.featureMedicine.ui.list.mapper

import cz.vvoleman.phr.featureMedicine.presentation.list.model.ProductFormPresentationModel
import cz.vvoleman.phr.featureMedicine.ui.list.model.ProductFormUiModel

class ProductFormUiModelToPresentationMapper {
    fun toPresentation(model: ProductFormUiModel): ProductFormPresentationModel {
        return ProductFormPresentationModel(
            id = model.id,
            name = model.name
        )
    }

    fun toUi(model: ProductFormPresentationModel): ProductFormUiModel {
        return ProductFormUiModel(
            id = model.id,
            name = model.name
        )
    }
}
