package cz.vvoleman.phr.feature_medicine.data.datasource.room.medicine.mapper

import cz.vvoleman.phr.feature_medicine.data.datasource.room.medicine.PackagingDataSourceModel
import cz.vvoleman.phr.feature_medicine.data.datasource.room.medicine.dao.ProductFormDao
import cz.vvoleman.phr.feature_medicine.data.model.PackagingDataModel
import kotlinx.coroutines.flow.first

class PackagingDataSourceModelToDataMapper (
    private val productFormDao: ProductFormDao,
    private val productFormDataSourceModelToDataMapper: ProductFormDataSourceModelToDataMapper
        ){


    suspend fun toData(model: PackagingDataSourceModel): PackagingDataModel {
        val productForm = productFormDao.getById(model.product_form_id).first()

        return PackagingDataModel(
            form = productFormDataSourceModelToDataMapper.toData(productForm),
            packaging = model.packaging,
        )
    }

    fun toDataSource(model: PackagingDataModel): PackagingDataSourceModel {
        val productForm = productFormDataSourceModelToDataMapper.toDataSource(model.form)
        return PackagingDataSourceModel(
            product_form_id = productForm.id,
            packaging = model.packaging,
        )
    }

}