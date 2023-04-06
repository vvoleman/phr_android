package cz.vvoleman.phr.feature_medicine.data.datasource.room.medicine.mapper

import cz.vvoleman.phr.feature_medicine.data.datasource.room.medicine.SubstanceDataSourceModel
import cz.vvoleman.phr.feature_medicine.data.model.medicine.SubstanceDataModel

class SubstanceDataSourceModelToDataMapper {

    fun toData(model: SubstanceDataSourceModel): SubstanceDataModel {
        return SubstanceDataModel(
            id = model.id,
            name = model.name
        )
    }

    fun toDataSource(model: SubstanceDataModel): SubstanceDataSourceModel {
        return SubstanceDataSourceModel(
            id = model.id,
            name = model.name
        )
    }

}