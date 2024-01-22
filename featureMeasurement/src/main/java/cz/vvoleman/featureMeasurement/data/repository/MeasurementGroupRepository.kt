package cz.vvoleman.featureMeasurement.data.repository

import cz.vvoleman.featureMeasurement.data.datasource.room.MeasurementGroupDao
import cz.vvoleman.featureMeasurement.data.datasource.room.mapper.MeasurementGroupDataSourceModelToDataMapper
import cz.vvoleman.featureMeasurement.data.mapper.MeasurementGroupDataModelToDomainMapper
import cz.vvoleman.featureMeasurement.domain.model.core.MeasurementGroupDomainModel
import cz.vvoleman.featureMeasurement.domain.repository.GetMeasurementGroupRepository
import kotlinx.coroutines.flow.firstOrNull

class MeasurementGroupRepository(
    private val measurementGroupDao: MeasurementGroupDao,
    private val measurementGroupDataSourceMapper: MeasurementGroupDataSourceModelToDataMapper,
    private val measurementGroupDataMapper: MeasurementGroupDataModelToDomainMapper
) : GetMeasurementGroupRepository {

    override suspend fun getMeasurementGroup(id: String): MeasurementGroupDomainModel? {
        return measurementGroupDao.getById(id.toInt())
            .firstOrNull()
            ?.let { measurementGroupDataSourceMapper.toData(it) }
            ?.let { measurementGroupDataMapper.toDomain(it) }
    }
}
