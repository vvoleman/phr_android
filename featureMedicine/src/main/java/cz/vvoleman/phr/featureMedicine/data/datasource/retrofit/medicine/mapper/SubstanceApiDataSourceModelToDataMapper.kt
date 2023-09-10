package cz.vvoleman.phr.featureMedicine.data.datasource.retrofit.medicine.mapper

import cz.vvoleman.phr.featureMedicine.data.datasource.retrofit.medicine.SubstanceApiDataSourceModel
import cz.vvoleman.phr.featureMedicine.data.model.medicine.SubstanceAmountDataModel
import cz.vvoleman.phr.featureMedicine.data.model.medicine.SubstanceDataModel

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
