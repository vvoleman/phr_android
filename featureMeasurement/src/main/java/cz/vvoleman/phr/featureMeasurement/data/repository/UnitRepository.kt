package cz.vvoleman.phr.featureMeasurement.data.repository

import cz.vvoleman.phr.featureMeasurement.data.datasource.room.mapper.UnitGroupDataSourceModelToDataMapper
import cz.vvoleman.phr.featureMeasurement.data.datasource.room.unit.UnitGroupDao
import cz.vvoleman.phr.featureMeasurement.data.mapper.core.UnitGroupDataModelToDomainMapper
import cz.vvoleman.phr.featureMeasurement.domain.model.core.field.unit.UnitGroupDomainModel
import cz.vvoleman.phr.featureMeasurement.domain.repository.GetUnitGroupsRepository
import kotlinx.coroutines.flow.first

class UnitRepository(
    private val unitGroupDao: UnitGroupDao,
    private val unitGroupDataSourceMapper: UnitGroupDataSourceModelToDataMapper,
    private val unitGroupDataMapper: UnitGroupDataModelToDomainMapper
) : GetUnitGroupsRepository {

    override suspend fun getUnitGroups(): List<UnitGroupDomainModel> {
        return unitGroupDao.getAll().first()
            .map { unitGroupDataSourceMapper.toData(it) }
            .map { unitGroupDataMapper.toDomain(it) }
    }
}
