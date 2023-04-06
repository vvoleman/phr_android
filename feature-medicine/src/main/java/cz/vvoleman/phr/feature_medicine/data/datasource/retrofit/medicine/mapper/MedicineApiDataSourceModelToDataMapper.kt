package cz.vvoleman.phr.feature_medicine.data.datasource.retrofit.medicine.mapper

import cz.vvoleman.phr.feature_medicine.data.datasource.retrofit.medicine.MedicineApiDataSourceModel
import cz.vvoleman.phr.feature_medicine.data.model.MedicineDataModel

class MedicineApiDataSourceModelToDataMapper(
    private val packagingMapper: PackagingApiDataSourceModelToDataMapper,
    private val substanceMapper: SubstanceApiDataSourceModelToDataMapper,
) {

    fun toData(model: MedicineApiDataSourceModel): MedicineDataModel {
        return MedicineDataModel(
            id = model.id,
            name = model.name,
            country = model.country,
            packaging = packagingMapper.toData(model.packaging),
            substances = model.substances.map { substanceMapper.toData(it) },
        )
    }

}