package cz.vvoleman.phr.featureMedicine.presentation.mapper.list

import cz.vvoleman.phr.featureMedicine.domain.model.medicine.PackagingDomainModel
import cz.vvoleman.phr.featureMedicine.presentation.model.list.PackagingPresentationModel

class PackagingPresentationModelToDomainMapper(
    private val formMapper: ProductFormPresentationModelToDomainMapper
) {
    fun toDomain(presentation: PackagingPresentationModel): PackagingDomainModel =
        PackagingDomainModel(
            form = formMapper.toDomain(presentation.form),
            packaging = presentation.packaging
        )

    fun toPresentation(domain: PackagingDomainModel): PackagingPresentationModel =
        PackagingPresentationModel(
            form = formMapper.toPresentation(domain.form),
            packaging = domain.packaging
        )
}
