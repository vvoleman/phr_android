package cz.vvoleman.phr.featureMedicine.data.repository

import android.util.Log
import cz.vvoleman.phr.featureMedicine.data.datasource.room.schedule.dao.MedicineScheduleDao
import cz.vvoleman.phr.featureMedicine.data.datasource.room.schedule.dao.ScheduleItemDao
import cz.vvoleman.phr.featureMedicine.data.datasource.room.schedule.mapper.MedicineScheduleDataSourceModelToDataMapper
import cz.vvoleman.phr.featureMedicine.data.datasource.room.schedule.mapper.SaveMedicineScheduleDataSourceModelToDataMapper
import cz.vvoleman.phr.featureMedicine.data.datasource.room.schedule.mapper.ScheduleItemDataSourceModelToDataMapper
import cz.vvoleman.phr.featureMedicine.data.mapper.schedule.MedicineScheduleDataModelToDomainMapper
import cz.vvoleman.phr.featureMedicine.data.mapper.schedule.SaveMedicineScheduleDomainModelToDataMapper
import cz.vvoleman.phr.featureMedicine.data.mapper.schedule.ScheduleItemDataModelToDomainMapper
import cz.vvoleman.phr.featureMedicine.domain.model.schedule.MedicineScheduleDomainModel
import cz.vvoleman.phr.featureMedicine.domain.model.schedule.ScheduleItemDomainModel
import cz.vvoleman.phr.featureMedicine.domain.model.schedule.save.SaveMedicineScheduleDomainModel
import cz.vvoleman.phr.featureMedicine.domain.repository.DeleteMedicineScheduleRepository
import cz.vvoleman.phr.featureMedicine.domain.repository.GetMedicineScheduleByIdRepository
import cz.vvoleman.phr.featureMedicine.domain.repository.GetScheduleByMedicineRepository
import cz.vvoleman.phr.featureMedicine.domain.repository.SaveMedicineScheduleRepository
import cz.vvoleman.phr.featureMedicine.domain.repository.SaveScheduleItemRepository
import cz.vvoleman.phr.featureMedicine.domain.repository.timeline.GetSchedulesByPatientRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull

class ScheduleRepository(
    private val medicineScheduleDao: MedicineScheduleDao,
    private val medicineScheduleDataSourceMapper: MedicineScheduleDataSourceModelToDataMapper,
    private val medicineScheduleDataMapper: MedicineScheduleDataModelToDomainMapper,
    private val saveMedicineDataMapper: SaveMedicineScheduleDomainModelToDataMapper,
    private val saveMedicineDataSourceMapper: SaveMedicineScheduleDataSourceModelToDataMapper,
    private val scheduleItemDao: ScheduleItemDao,
    private val scheduleItemDataSourceMapper: ScheduleItemDataSourceModelToDataMapper,
    private val scheduleItemDataMapper: ScheduleItemDataModelToDomainMapper,
) : GetScheduleByMedicineRepository,
    SaveMedicineScheduleRepository,
    SaveScheduleItemRepository,
    GetSchedulesByPatientRepository,
    GetMedicineScheduleByIdRepository,
    DeleteMedicineScheduleRepository {

    override suspend fun getScheduleByMedicine(
        medicineId: String,
        patientId: String
    ): List<MedicineScheduleDomainModel> {
        return medicineScheduleDao.getByMedicine(medicineId, patientId).first()
            .map { medicineScheduleDataSourceMapper.toData(it) }
            .map { medicineScheduleDataMapper.toDomain(it) }
    }

    override suspend fun saveMedicineSchedule(
        medicineSchedule: SaveMedicineScheduleDomainModel
    ): MedicineScheduleDomainModel {
        val saveModel = saveMedicineDataMapper
            .toData(medicineSchedule)
            .let { saveMedicineDataSourceMapper.toDataSource(it) }

        val id = medicineScheduleDao.insert(saveModel)

        Log.d("ScheduleRepository", "saveMedicineSchedule: $id")

        // Remove all schedule items
        if (medicineSchedule.id != null) {
            scheduleItemDao.deleteAll(medicineSchedule.id)
        }

        // Save Schedule Items
        medicineSchedule.schedules.forEach {
            Log.d("ScheduleRepository", "save schedule Item: $it")
            saveScheduleItemOnly(it, id.toString())
        }

        Log.d("ScheduleRepository", "medicineSchedules: ${medicineSchedule.schedules}")

        val model = medicineScheduleDao.getById(id.toInt()).first()

        return medicineScheduleDataSourceMapper
            .toData(model)
            .let { medicineScheduleDataMapper.toDomain(it) }
    }

    override suspend fun saveScheduleItem(
        scheduleItem: ScheduleItemDomainModel,
        scheduleId: String
    ): ScheduleItemDomainModel {
        val id = saveScheduleItemOnly(scheduleItem, scheduleId)

        val model = scheduleItemDao.getById(id).first()

        return scheduleItemDataSourceMapper
            .toData(model)
            .let { scheduleItemDataMapper.toDomain(it) }
    }

    override suspend fun getSchedulesByPatient(patientId: String): List<MedicineScheduleDomainModel> {
        return medicineScheduleDao.getAll(patientId.toInt()).first()
            .map { medicineScheduleDataSourceMapper.toData(it) }
            .map { medicineScheduleDataMapper.toDomain(it) }
    }

    override suspend fun deleteMedicineSchedule(medicineSchedule: MedicineScheduleDomainModel): Boolean {
        // Delete all schedule items and schedules
        scheduleItemDao.deleteAll(medicineSchedule.id)
        medicineScheduleDao.delete(medicineSchedule.id)

        // Check if schedule was deleted
        return medicineScheduleDao.getById(medicineSchedule.id.toInt()).firstOrNull() == null
    }

    private suspend fun saveScheduleItemOnly(
        scheduleItem: ScheduleItemDomainModel,
        scheduleId: String
    ): String {
        val saveModel = scheduleItemDataMapper
            .toData(scheduleItem)
            .let { scheduleItemDataSourceMapper.toDataSource(it, scheduleId) }

        return scheduleItemDao.insert(saveModel).toString()
    }

    override suspend fun getMedicineScheduleById(id: String): MedicineScheduleDomainModel? {
        return medicineScheduleDao.getById(id.toInt()).firstOrNull()
            ?.let { medicineScheduleDataSourceMapper.toData(it) }
            ?.let { medicineScheduleDataMapper.toDomain(it) }
    }
}
