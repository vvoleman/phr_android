package cz.vvoleman.phr.feature_medicine.data.mapper

import cz.vvoleman.phr.feature_medicine.data.datasource.retrofit.medicine.SubstanceApiDataSourceModel
import cz.vvoleman.phr.feature_medicine.data.datasource.room.medicine.SubstanceAmountDataSourceModel
import cz.vvoleman.phr.feature_medicine.data.datasource.room.medicine.SubstanceDataSourceModel
import cz.vvoleman.phr.feature_medicine.domain.model.medicine.SubstanceAmountDomainModel
import cz.vvoleman.phr.feature_medicine.domain.model.medicine.SubstanceDomainModel

class SubstanceDataSourceModelToDomainMapper {

    fun toDomain(model: SubstanceAmountDataSourceModel): SubstanceAmountDomainModel {
        return SubstanceAmountDomainModel(
            substance = SubstanceDomainModel(
                id = model.substance.id,
                name = model.substance.name
            ),
            amount = model.amount,
            unit = ""
        )
    }

    fun toDataSource(model: SubstanceAmountDomainModel, medicineId: String): SubstanceAmountDataSourceModel {
        return SubstanceAmountDataSourceModel(
            substance = SubstanceDataSourceModel(
                id = model.substance.id,
                name = model.substance.name
            ),
            amount = model.amount,
            medicine_id = medicineId
        )
    }

}