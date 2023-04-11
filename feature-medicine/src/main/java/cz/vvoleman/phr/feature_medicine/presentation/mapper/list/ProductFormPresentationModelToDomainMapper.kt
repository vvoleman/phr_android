package cz.vvoleman.phr.feature_medicine.presentation.mapper.list

import cz.vvoleman.phr.feature_medicine.domain.model.medicine.ProductFormDomainModel
import cz.vvoleman.phr.feature_medicine.presentation.model.list.ProductFormPresentationModel

class ProductFormPresentationModelToDomainMapper {
    fun toDomain(model: ProductFormPresentationModel): ProductFormDomainModel {
        return ProductFormDomainModel(
            id = model.id,
            name = model.name,
        )
    }

    fun toPresentation(model: ProductFormDomainModel): ProductFormPresentationModel {
        return ProductFormPresentationModel(
            id = model.id,
            name = model.name,
        )
    }
}