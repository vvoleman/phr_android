package cz.vvoleman.phr.featureMeasurement.data.repository

import cz.vvoleman.phr.featureMeasurement.data.datasource.room.field.NumericFieldDao
import cz.vvoleman.phr.featureMeasurement.data.datasource.room.mapper.NumericFieldDataSourceModelToDataMapper
import cz.vvoleman.phr.featureMeasurement.data.mapper.core.MeasurementGroupFieldDataToDomainMapper
import cz.vvoleman.phr.featureMeasurement.data.model.core.field.NumericFieldDataModel
import cz.vvoleman.phr.featureMeasurement.domain.model.core.MeasurementGroupFieldDomain

class MeasurementGroupFieldRepository(
    private val fieldMapper: MeasurementGroupFieldDataToDomainMapper,
    private val numericFieldDao: NumericFieldDao,
    private val numericFieldMapper: NumericFieldDataSourceModelToDataMapper
) {

    suspend fun saveMeasurementGroupField(model: MeasurementGroupFieldDomain, measurementGroupId: String): String {
        return when (val field = fieldMapper.toData(model)) {
            is NumericFieldDataModel -> saveNumericField(field, measurementGroupId)
            else -> throw IllegalArgumentException("Unknown field type")
        }
    }

    private suspend fun saveNumericField(model: NumericFieldDataModel, measurementGroupId: String): String {
        val field = numericFieldMapper.toDataSource(model, measurementGroupId)
        return numericFieldDao.insert(field).toString()
    }
}
