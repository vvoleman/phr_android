package cz.vvoleman.phr.feature_medicine.data.mapper

import cz.vvoleman.phr.feature_medicine.data.datasource.retrofit.medicine.MedicineApiDataSourceModel
import cz.vvoleman.phr.feature_medicine.data.datasource.room.medicine.MedicineDataSourceModel
import cz.vvoleman.phr.feature_medicine.data.datasource.room.medicine.MedicineWithDetails

class MedicineApiModelToDbMapper(
    private val substanceMapper: SubstanceApiModelToDbMapper,
    private val packagingMapper: PackagingApiModelToDbMapper
) {

    fun toDb(model: MedicineApiDataSourceModel): MedicineWithDetails {
        val medicine = MedicineDataSourceModel(
            id = model.id,
            name = model.name,
            country = model.country
        )

        return MedicineWithDetails(
            medicine = medicine,
            substances = model.substances.map { substanceMapper.toDb(it, model.id) },
            packagingDataSourceModel = packagingMapper.toDb(model.packaging, model.id)
        )
    }

}