package cz.vvoleman.phr.featureMeasurement.data.repository

import cz.vvoleman.phr.featureMeasurement.data.datasource.room.MeasurementGroupEntryDao
import cz.vvoleman.phr.featureMeasurement.data.datasource.room.mapper.MeasurementGroupEntryDataSourceModelToDataMapper
import cz.vvoleman.phr.featureMeasurement.data.mapper.core.MeasurementGroupEntryDataModelToDomainMapper
import cz.vvoleman.phr.featureMeasurement.domain.model.core.MeasurementGroupEntryDomainModel
import cz.vvoleman.phr.featureMeasurement.domain.repository.DeleteEntriesByMeasurementGroupRepository
import cz.vvoleman.phr.featureMeasurement.domain.repository.DeleteEntryRepository
import cz.vvoleman.phr.featureMeasurement.domain.repository.GetEntryByIdRepository
import kotlinx.coroutines.flow.firstOrNull

class MeasurementGroupEntryRepository(
    private val entryDao: MeasurementGroupEntryDao,
    private val entryDataSourceMapper: MeasurementGroupEntryDataSourceModelToDataMapper,
    private val entryDataMapper: MeasurementGroupEntryDataModelToDomainMapper,
) : GetEntryByIdRepository, DeleteEntriesByMeasurementGroupRepository, DeleteEntryRepository {

    override suspend fun getEntryById(id: String): MeasurementGroupEntryDomainModel? {
        return entryDao.getById(id).firstOrNull()
            ?.let { entryDataSourceMapper.toData(it) }
            ?.let { entryDataMapper.toDomain(it) }
    }

    override suspend fun deleteEntriesByMeasurementGroup(measurementGroupId: String) {
        entryDao.deleteByMeasurementGroupId(measurementGroupId)
    }

    override suspend fun deleteEntry(entryId: String) {
        entryDao.delete(entryId)
    }
}
