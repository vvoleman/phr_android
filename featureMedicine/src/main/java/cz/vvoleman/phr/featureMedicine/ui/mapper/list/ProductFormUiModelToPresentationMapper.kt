package cz.vvoleman.phr.featureMedicine.ui.mapper.list

import cz.vvoleman.phr.featureMedicine.presentation.model.list.ProductFormPresentationModel
import cz.vvoleman.phr.featureMedicine.ui.model.list.ProductFormUiModel

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
