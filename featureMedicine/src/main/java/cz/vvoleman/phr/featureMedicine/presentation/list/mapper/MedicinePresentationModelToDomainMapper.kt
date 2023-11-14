package cz.vvoleman.phr.featureMedicine.presentation.list.mapper

import cz.vvoleman.phr.featureMedicine.domain.model.medicine.MedicineDomainModel
import cz.vvoleman.phr.featureMedicine.presentation.list.model.MedicinePresentationModel

class MedicinePresentationModelToDomainMapper(
    private val packagingMapper: PackagingPresentationModelToDomainMapper,
    private val substanceAmountMapper: SubstanceAmountPresentationModelToDomainMapper
) {
    fun toDomain(presentation: MedicinePresentationModel): MedicineDomainModel = MedicineDomainModel(
        id = presentation.id,
        name = presentation.name,
        packaging = packagingMapper.toDomain(presentation.packaging),
        country = presentation.country,
        substances = presentation.substances.map { substanceAmountMapper.toDomain(it) }
    )

    fun toPresentation(domain: MedicineDomainModel): MedicinePresentationModel = MedicinePresentationModel(
        id = domain.id,
        name = domain.name,
        packaging = packagingMapper.toPresentation(domain.packaging),
        country = domain.country,
        substances = domain.substances.map { substanceAmountMapper.toPresentation(it) }
    )
}
