package cz.vvoleman.phr.feature_medicine.data.mapper.medicine

import cz.vvoleman.phr.feature_medicine.data.model.medicine.SubstanceDataModel
import cz.vvoleman.phr.feature_medicine.domain.model.medicine.SubstanceDomainModel

class SubstanceDataModelToDomainMapper {

    fun toDomain(model: SubstanceDataModel): SubstanceDomainModel {
        return SubstanceDomainModel(
            id = model.id,
            name = model.name
        )
    }

    fun toData(model: SubstanceDomainModel): SubstanceDataModel {
        return SubstanceDataModel(
            id = model.id,
            name = model.name
        )
    }
}
