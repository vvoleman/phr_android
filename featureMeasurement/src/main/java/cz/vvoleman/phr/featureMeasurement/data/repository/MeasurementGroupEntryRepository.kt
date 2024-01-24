package cz.vvoleman.phr.featureMeasurement.data.repository

import cz.vvoleman.phr.featureMeasurement.data.datasource.room.MeasurementGroupEntryDao
import cz.vvoleman.phr.featureMeasurement.domain.repository.DeleteEntriesByMeasurementGroupRepository
import cz.vvoleman.phr.featureMeasurement.domain.repository.DeleteEntryRepository

class MeasurementGroupEntryRepository(
    private val entryDao: MeasurementGroupEntryDao,
) : DeleteEntriesByMeasurementGroupRepository, DeleteEntryRepository {

    override suspend fun deleteEntriesByMeasurementGroup(measurementGroupId: String) {
        entryDao.deleteByMeasurementGroupId(measurementGroupId)
    }

    override suspend fun deleteEntry(entryId: String) {
        entryDao.delete(entryId)
    }
}
