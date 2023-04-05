package cz.vvoleman.phr.feature_medicine.data.mapper

import cz.vvoleman.phr.feature_medicine.data.datasource.room.medicine.MedicineDataSourceModel
import cz.vvoleman.phr.feature_medicine.data.datasource.room.medicine.MedicineWithDetails
import cz.vvoleman.phr.feature_medicine.domain.model.medicine.MedicineDomainModel

class MedicineDataSourceModelToDomainMapper(
    private val substanceMapper: SubstanceDataSourceModelToDomainMapper,
    private val packagingMapper: PackagingDataSourceModelToDomainMapper
) {

    fun toDomain(model: MedicineWithDetails): MedicineDomainModel {
        return MedicineDomainModel(
            id = model.medicine.id,
            name = model.medicine.name,
            country = model.medicine.country,
            packaging = model.packagingDataSourceModel.let { packagingMapper.toDomain(it) },
            substances = model.substances.map { substanceMapper.toDomain(it) }
        )
    }

    fun toDataSource(model: MedicineDomainModel): MedicineWithDetails {
        return MedicineWithDetails(
            medicine = MedicineDataSourceModel(
                id = model.id,
                name = model.name,
                country = model.country
            ),
            packagingDataSourceModel = packagingMapper.toDataSource(model.packaging, model.id),
            substances = model.substances.map { substanceMapper.toDataSource(it, model.id) }
        )
    }

}