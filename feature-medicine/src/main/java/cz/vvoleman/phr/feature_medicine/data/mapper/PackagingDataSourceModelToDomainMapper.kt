package cz.vvoleman.phr.feature_medicine.data.mapper

import cz.vvoleman.phr.feature_medicine.data.datasource.room.medicine.PackagingDataSourceModel
import cz.vvoleman.phr.feature_medicine.domain.model.medicine.PackagingDomainModel
import cz.vvoleman.phr.feature_medicine.domain.model.medicine.ProductFormDomainModel

class PackagingDataSourceModelToDomainMapper {

    fun toDomain(model: PackagingDataSourceModel): PackagingDomainModel {
        return PackagingDomainModel(
            form = ProductFormDomainModel(
                id = model.productForm.id,
                name = model.productForm.name
            ),
            packaging = model.packaging
        )
    }

    fun toDataSource(model: PackagingDomainModel, medicineId: String): PackagingDataSourceModel {
        return PackagingDataSourceModel(
            productForm = cz.vvoleman.phr.feature_medicine.data.datasource.room.medicine.ProductFormDataSourceModel(
                id = model.form.id,
                name = model.form.name
            ),
            packaging = model.packaging,
            medicine_id = medicineId
        )
    }

}