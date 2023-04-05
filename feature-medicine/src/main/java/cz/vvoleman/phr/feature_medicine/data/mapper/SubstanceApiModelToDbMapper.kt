package cz.vvoleman.phr.feature_medicine.data.mapper

import cz.vvoleman.phr.feature_medicine.data.datasource.retrofit.medicine.SubstanceApiDataSourceModel
import cz.vvoleman.phr.feature_medicine.data.datasource.room.medicine.SubstanceAmountDataSourceModel
import cz.vvoleman.phr.feature_medicine.data.datasource.room.medicine.SubstanceDataSourceModel

class SubstanceApiModelToDbMapper {

    fun toDb(model: SubstanceApiDataSourceModel, medicineId: String): SubstanceAmountDataSourceModel {
        return SubstanceAmountDataSourceModel(
            substance = SubstanceDataSourceModel(model.id, model.name),
            amount = model.strength,
            medicine_id = medicineId
        )
    }

}