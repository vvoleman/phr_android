package cz.vvoleman.phr.feature_medicine.data.mapper

import cz.vvoleman.phr.feature_medicine.data.datasource.retrofit.medicine.PackagingApiDataSourceModel
import cz.vvoleman.phr.feature_medicine.data.datasource.room.medicine.PackagingDataSourceModel
import cz.vvoleman.phr.feature_medicine.data.datasource.room.medicine.ProductFormDataSourceModel

class PackagingApiModelToDbMapper {

    fun toDb(model: PackagingApiDataSourceModel, medicineId: String): PackagingDataSourceModel {
        return PackagingDataSourceModel(
            productForm = ProductFormDataSourceModel(model.form.id, model.form.name),
            medicine_id = medicineId,
            packaging = model.packaging,
        )
    }

}