package cz.vvoleman.phr.featureMedicine.data.datasource.retrofit.medicine.mapper

import cz.vvoleman.phr.featureMedicine.data.datasource.retrofit.medicine.PackagingApiDataSourceModel
import cz.vvoleman.phr.featureMedicine.data.model.medicine.PackagingDataModel

class PackagingApiDataSourceModelToDataMapper(
    private val productFormApiDataSourceModelToDataMapper: ProductFormApiDataSourceModelToDataMapper
) {

    fun toData(model: PackagingApiDataSourceModel): PackagingDataModel {
        return PackagingDataModel(
            form = productFormApiDataSourceModelToDataMapper.toData(model.form),
            packaging = model.packaging
        )
    }
}
