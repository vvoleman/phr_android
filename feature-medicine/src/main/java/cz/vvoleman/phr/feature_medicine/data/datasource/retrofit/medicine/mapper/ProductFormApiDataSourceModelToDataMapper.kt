package cz.vvoleman.phr.feature_medicine.data.datasource.retrofit.medicine.mapper

import cz.vvoleman.phr.feature_medicine.data.datasource.retrofit.medicine.ProductFormApiDataSourceModel
import cz.vvoleman.phr.feature_medicine.data.model.medicine.ProductFormDataModel

class ProductFormApiDataSourceModelToDataMapper {

    fun toData(model: ProductFormApiDataSourceModel): ProductFormDataModel {
        return ProductFormDataModel(
            id = model.id,
            name = model.name
        )
    }
}
