package cz.vvoleman.phr.featureMedicine.data.mapper.medicine

import cz.vvoleman.phr.featureMedicine.data.model.medicine.SubstanceAmountDataModel
import cz.vvoleman.phr.featureMedicine.domain.model.medicine.SubstanceAmountDomainModel

class SubstanceAmountDataModelToDomainMapper(
    private val substanceDataModelToDomainMapper: SubstanceDataModelToDomainMapper
) {

    fun toDomain(model: SubstanceAmountDataModel): SubstanceAmountDomainModel {
        return SubstanceAmountDomainModel(
            substance = substanceDataModelToDomainMapper.toDomain(model.substance),
            amount = model.amount,
            unit = ""
        )
    }

    fun toData(model: SubstanceAmountDomainModel): SubstanceAmountDataModel {
        return SubstanceAmountDataModel(
            substance = substanceDataModelToDomainMapper.toData(model.substance),
            amount = model.amount,
            unit = ""
        )
    }
}
