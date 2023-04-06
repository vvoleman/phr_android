package cz.vvoleman.phr.feature_medicine.data.datasource.room.medicine.mapper

import cz.vvoleman.phr.feature_medicine.data.datasource.room.medicine.MedicineDataSourceModel
import cz.vvoleman.phr.feature_medicine.data.model.MedicineDataModel

class MedicineDataSourceModelToDataMapper(
    private val packagingMapper: PackagingDataSourceModelToDataMapper,
    private val substanceMapper: SubstanceAmountDataSourceModelToDataMapper,
) {

    suspend fun toData(model: MedicineDataSourceModel): MedicineDataModel {
        return MedicineDataModel(
            id = model.id,
            name = model.name,
            country = model.country,
            packaging = packagingMapper.toData(model.packaging),
            substances = model.substances.map { substanceMapper.toData(it) },
        )
    }

    fun toDataSource(model: MedicineDataModel): MedicineDataSourceModel {
        return MedicineDataSourceModel(
            id = model.id,
            name = model.name,
            country = model.country,
            packaging = packagingMapper.toDataSource(model.packaging),
            substances = model.substances.map { substanceMapper.toDataSource(it) },
        )
    }

}