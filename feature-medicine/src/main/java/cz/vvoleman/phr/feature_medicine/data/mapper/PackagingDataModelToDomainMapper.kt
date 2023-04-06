package cz.vvoleman.phr.feature_medicine.data.mapper

import cz.vvoleman.phr.feature_medicine.data.model.PackagingDataModel
import cz.vvoleman.phr.feature_medicine.domain.model.medicine.PackagingDomainModel

class PackagingDataModelToDomainMapper(
    private val productFormMapper: ProductFormDataModelToDomainMapper,
) {

    fun toDomain(model: PackagingDataModel): PackagingDomainModel {
        return PackagingDomainModel(
            form = productFormMapper.toDomain(model.form),
            packaging = model.packaging,
        )
    }

    fun toData(model: PackagingDomainModel): PackagingDataModel {
        return PackagingDataModel(
            form = productFormMapper.toData(model.form),
            packaging = model.packaging,
        )
    }

}