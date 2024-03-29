package cz.vvoleman.phr.featureMedicine.data.datasource.retrofit.medicine.mapper

import cz.vvoleman.phr.featureMedicine.data.datasource.retrofit.medicine.MedicineApiDataSourceModel
import cz.vvoleman.phr.featureMedicine.data.model.medicine.MedicineDataModel

class MedicineApiDataSourceModelToDataMapper(
    private val packagingMapper: PackagingApiDataSourceModelToDataMapper,
    private val substanceMapper: SubstanceApiDataSourceModelToDataMapper
) {

    fun toData(model: MedicineApiDataSourceModel): MedicineDataModel {
        return MedicineDataModel(
            id = model.id,
            name = model.name,
            country = model.country,
            packaging = packagingMapper.toData(model.packaging),
            substances = model.substances.map { substanceMapper.toData(it) }
        )
    }
}
