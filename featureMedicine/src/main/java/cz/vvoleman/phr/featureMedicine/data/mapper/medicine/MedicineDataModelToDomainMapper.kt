package cz.vvoleman.phr.featureMedicine.data.mapper.medicine

import cz.vvoleman.phr.featureMedicine.data.model.medicine.MedicineDataModel
import cz.vvoleman.phr.featureMedicine.domain.model.medicine.MedicineDomainModel

class MedicineDataModelToDomainMapper(
    private val packagingMapper: PackagingDataModelToDomainMapper,
    private val substanceAmountMapper: SubstanceAmountDataModelToDomainMapper
) {

    fun toDomain(model: MedicineDataModel): MedicineDomainModel {
        return MedicineDomainModel(
            id = model.id,
            name = model.name,
            packaging = packagingMapper.toDomain(model.packaging),
            country = model.country,
            substances = model.substances.map { substanceAmountMapper.toDomain(it) }
        )
    }

    fun toData(model: MedicineDomainModel): MedicineDataModel {
        return MedicineDataModel(
            id = model.id,
            name = model.name,
            packaging = packagingMapper.toData(model.packaging),
            country = model.country,
            substances = model.substances.map { substanceAmountMapper.toData(it) }
        )
    }
}
