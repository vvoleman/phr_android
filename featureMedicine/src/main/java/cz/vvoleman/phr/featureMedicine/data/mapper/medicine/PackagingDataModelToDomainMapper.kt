package cz.vvoleman.phr.featureMedicine.data.mapper.medicine

import cz.vvoleman.phr.featureMedicine.data.model.medicine.PackagingDataModel
import cz.vvoleman.phr.featureMedicine.domain.model.medicine.PackagingDomainModel

class PackagingDataModelToDomainMapper(
    private val productFormMapper: ProductFormDataModelToDomainMapper
) {

    fun toDomain(model: PackagingDataModel): PackagingDomainModel {
        return PackagingDomainModel(
            form = productFormMapper.toDomain(model.form),
            packaging = model.packaging
        )
    }

    fun toData(model: PackagingDomainModel): PackagingDataModel {
        return PackagingDataModel(
            form = productFormMapper.toData(model.form),
            packaging = model.packaging
        )
    }
}
