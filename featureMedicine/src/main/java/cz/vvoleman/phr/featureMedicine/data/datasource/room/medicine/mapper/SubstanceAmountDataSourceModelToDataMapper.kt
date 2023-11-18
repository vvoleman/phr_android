package cz.vvoleman.phr.featureMedicine.data.datasource.room.medicine.mapper

import cz.vvoleman.phr.featureMedicine.data.datasource.room.medicine.SubstanceAmountDataSourceModel
import cz.vvoleman.phr.featureMedicine.data.datasource.room.medicine.dao.SubstanceDao
import cz.vvoleman.phr.featureMedicine.data.model.medicine.SubstanceAmountDataModel
import kotlinx.coroutines.flow.first

class SubstanceAmountDataSourceModelToDataMapper(
    private val substanceDao: SubstanceDao,
    private val substanceDataSourceModelToDataMapper: SubstanceDataSourceModelToDataMapper
) {

    suspend fun toData(model: SubstanceAmountDataSourceModel): SubstanceAmountDataModel {
        val substance = substanceDao.getById(model.substanceId).first()
        return SubstanceAmountDataModel(
            substance = substanceDataSourceModelToDataMapper.toData(substance),
            amount = model.amount,
            unit = ""
        )
    }

    fun toDataSource(model: SubstanceAmountDataModel): SubstanceAmountDataSourceModel {
        val substance = substanceDataSourceModelToDataMapper.toDataSource(model.substance)
        return SubstanceAmountDataSourceModel(
            substanceId = substance.id,
            amount = model.amount
        )
    }
}
