package cz.vvoleman.phr.featureMedicine.presentation.list.mapper

import cz.vvoleman.phr.featureMedicine.domain.model.medicine.ProductFormDomainModel
import cz.vvoleman.phr.featureMedicine.presentation.list.model.ProductFormPresentationModel

class ProductFormPresentationModelToDomainMapper {
    fun toDomain(model: ProductFormPresentationModel): ProductFormDomainModel {
        return ProductFormDomainModel(
            id = model.id,
            name = model.name
        )
    }

    fun toPresentation(model: ProductFormDomainModel): ProductFormPresentationModel {
        return ProductFormPresentationModel(
            id = model.id,
            name = model.name
        )
    }
}
