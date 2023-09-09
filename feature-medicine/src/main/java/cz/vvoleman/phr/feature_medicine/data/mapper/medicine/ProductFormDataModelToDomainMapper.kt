package cz.vvoleman.phr.feature_medicine.data.mapper.medicine

import cz.vvoleman.phr.feature_medicine.data.model.medicine.ProductFormDataModel
import cz.vvoleman.phr.feature_medicine.domain.model.medicine.ProductFormDomainModel

class ProductFormDataModelToDomainMapper {

    fun toDomain(model: ProductFormDataModel): ProductFormDomainModel {
        return ProductFormDomainModel(
            id = model.id,
            name = model.name
        )
    }

    fun toData(model: ProductFormDomainModel): ProductFormDataModel {
        return ProductFormDataModel(
            id = model.id,
            name = model.name
        )
    }
}
