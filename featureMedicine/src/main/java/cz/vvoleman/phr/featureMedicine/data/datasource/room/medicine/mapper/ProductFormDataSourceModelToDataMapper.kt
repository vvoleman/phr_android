package cz.vvoleman.phr.featureMedicine.data.datasource.room.medicine.mapper

import cz.vvoleman.phr.featureMedicine.data.datasource.room.medicine.ProductFormDataSourceModel
import cz.vvoleman.phr.featureMedicine.data.model.medicine.ProductFormDataModel

class ProductFormDataSourceModelToDataMapper {

    fun toData(model: ProductFormDataSourceModel): ProductFormDataModel {
        return ProductFormDataModel(
            id = model.id,
            name = model.name
        )
    }

    fun toDataSource(model: ProductFormDataModel): ProductFormDataSourceModel {
        return ProductFormDataSourceModel(
            id = model.id,
            name = model.name
        )
    }
}
