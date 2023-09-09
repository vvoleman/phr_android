package cz.vvoleman.phr.feature_medicine.data.datasource.retrofit.medicine.mapper

import cz.vvoleman.phr.feature_medicine.data.datasource.retrofit.medicine.SubstanceApiDataSourceModel
import cz.vvoleman.phr.feature_medicine.data.model.medicine.SubstanceAmountDataModel
import cz.vvoleman.phr.feature_medicine.data.model.medicine.SubstanceDataModel

class SubstanceApiDataSourceModelToDataMapper {

    fun toData(model: SubstanceApiDataSourceModel): SubstanceAmountDataModel {
        return SubstanceAmountDataModel(
            substance = SubstanceDataModel(
                id = model.id,
                name = model.name
            ),
            amount = model.strength,
            unit = ""
        )
    }
}
