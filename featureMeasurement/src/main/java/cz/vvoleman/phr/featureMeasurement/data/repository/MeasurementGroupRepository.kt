package cz.vvoleman.phr.featureMeasurement.data.repository

import android.util.Log
import cz.vvoleman.phr.featureMeasurement.data.datasource.room.MeasurementGroupDao
import cz.vvoleman.phr.featureMeasurement.data.datasource.room.MeasurementGroupScheduleItemDao
import cz.vvoleman.phr.featureMeasurement.data.datasource.room.mapper.MeasurementGroupDataSourceModelToDataMapper
import cz.vvoleman.phr.featureMeasurement.data.datasource.room.mapper.MeasurementGroupScheduleItemDataSourceModelToDataMapper
import cz.vvoleman.phr.featureMeasurement.data.mapper.addEdit.SaveMeasurementGroupDataModelToDomainMapper
import cz.vvoleman.phr.featureMeasurement.data.mapper.addEdit.SaveMeasurementGroupScheduleItemDataModelToDomainMapper
import cz.vvoleman.phr.featureMeasurement.data.mapper.core.MeasurementGroupDataModelToDomainMapper
import cz.vvoleman.phr.featureMeasurement.domain.model.addEdit.SaveMeasurementGroupDomainModel
import cz.vvoleman.phr.featureMeasurement.domain.model.addEdit.SaveMeasurementGroupScheduleItemDomainModel
import cz.vvoleman.phr.featureMeasurement.domain.model.core.MeasurementGroupDomainModel
import cz.vvoleman.phr.featureMeasurement.domain.repository.GetMeasurementGroupRepository
import cz.vvoleman.phr.featureMeasurement.domain.repository.GetMeasurementGroupsByPatientRepository
import cz.vvoleman.phr.featureMeasurement.domain.repository.SaveMeasurementGroupRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

class MeasurementGroupRepository(
    private val saveMeasurementGroupMapper: SaveMeasurementGroupDataModelToDomainMapper,
    private val measurementGroupDao: MeasurementGroupDao,
    private val measurementGroupDataSourceMapper: MeasurementGroupDataSourceModelToDataMapper,
    private val measurementGroupDataMapper: MeasurementGroupDataModelToDomainMapper,
    private val saveScheduleItemMapper: SaveMeasurementGroupScheduleItemDataModelToDomainMapper,
    private val scheduleItemDataSourceMapper: MeasurementGroupScheduleItemDataSourceModelToDataMapper,
    private val scheduleItemDao: MeasurementGroupScheduleItemDao,
) : GetMeasurementGroupRepository, SaveMeasurementGroupRepository, GetMeasurementGroupsByPatientRepository {

    override suspend fun getMeasurementGroup(id: String): MeasurementGroupDomainModel? {
        return measurementGroupDao.getById(id.toInt())
            .firstOrNull()
            ?.let { measurementGroupDataSourceMapper.toData(it) }
            ?.let { measurementGroupDataMapper.toDomain(it) }
    }

    override suspend fun saveMeasurementGroup(model: SaveMeasurementGroupDomainModel): MeasurementGroupDomainModel? {
        val saveModel = saveMeasurementGroupMapper
            .toData(model)
            .let { measurementGroupDataSourceMapper.toDataSource(it) }

        val id = measurementGroupDao.insert(saveModel)

        Log.d("MeasurementGroupRepository", "MeasurementGroup saved with ID '$id'")

        // Remove all schedule items
        if (model.id != null) {
            scheduleItemDao.deleteByMeasurementGroup(model.id.toInt())
        }

        // Save each schedule item
        model.scheduleItems.forEach {
            saveScheduleItemOnly(it, id.toString())
        }

        Log.d("MeasurementGroupRepository", "Schedule items saved")

        // Retrieve updated measurement group
        return measurementGroupDao.getById(id.toInt()).firstOrNull()
            ?.let { measurementGroupDataSourceMapper.toData(it) }
            ?.let { measurementGroupDataMapper.toDomain(it) }
    }

    override suspend fun getMeasurementGroupsByPatient(patientId: String): List<MeasurementGroupDomainModel> {
        return measurementGroupDao.getByPatient(patientId.toInt())
            .map { measurementGroupDataSourceMapper.toData(it) }
            .map { measurementGroupDataMapper.toDomain(it) }
            .first()
    }

    private suspend fun saveScheduleItemOnly(
        scheduleItem: SaveMeasurementGroupScheduleItemDomainModel,
        scheduleId: String
    ): String {
        val saveModel = saveScheduleItemMapper
            .toData(scheduleItem)
            .let { scheduleItemDataSourceMapper.toDataSource(it, scheduleId) }

        return scheduleItemDao.insert(saveModel).toString()
    }
}
