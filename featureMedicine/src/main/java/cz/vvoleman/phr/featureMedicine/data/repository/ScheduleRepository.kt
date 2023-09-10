package cz.vvoleman.phr.featureMedicine.data.repository

import cz.vvoleman.phr.featureMedicine.data.datasource.room.schedule.dao.MedicineScheduleDao
import cz.vvoleman.phr.featureMedicine.data.datasource.room.schedule.dao.ScheduleItemDao
import cz.vvoleman.phr.featureMedicine.data.datasource.room.schedule.mapper.MedicineScheduleDataSourceModelToDataMapper
import cz.vvoleman.phr.featureMedicine.data.datasource.room.schedule.mapper.ScheduleItemDataSourceModelToDataMapper
import cz.vvoleman.phr.featureMedicine.data.mapper.schedule.MedicineScheduleDataModelToDomainMapper
import cz.vvoleman.phr.featureMedicine.data.mapper.schedule.SaveMedicineScheduleDomainModelToDataMapper
import cz.vvoleman.phr.featureMedicine.data.mapper.schedule.SaveScheduleItemDomainModelToDataMapper
import cz.vvoleman.phr.featureMedicine.data.mapper.schedule.ScheduleItemDataModelToDomainMapper
import cz.vvoleman.phr.featureMedicine.domain.model.schedule.MedicineScheduleDomainModel
import cz.vvoleman.phr.featureMedicine.domain.model.schedule.ScheduleItemDomainModel
import cz.vvoleman.phr.featureMedicine.domain.model.schedule.save.SaveMedicineScheduleDomainModel
import cz.vvoleman.phr.featureMedicine.domain.model.schedule.save.SaveScheduleItemDomainModel
import cz.vvoleman.phr.featureMedicine.domain.repository.GetScheduleByMedicineRepository
import cz.vvoleman.phr.featureMedicine.domain.repository.SaveMedicineScheduleRepository
import cz.vvoleman.phr.featureMedicine.domain.repository.SaveScheduleItemRepository
import cz.vvoleman.phr.featureMedicine.domain.repository.timeline.GetSchedulesByPatientRepository
import kotlinx.coroutines.flow.first

class ScheduleRepository(
    private val medicineScheduleDao: MedicineScheduleDao,
    private val medicineScheduleDataSourceMapper: MedicineScheduleDataSourceModelToDataMapper,
    private val medicineScheduleDataMapper: MedicineScheduleDataModelToDomainMapper,
    private val saveMedicineMapper: SaveMedicineScheduleDomainModelToDataMapper,
    private val scheduleItemDao: ScheduleItemDao,
    private val scheduleItemDataSourceMapper: ScheduleItemDataSourceModelToDataMapper,
    private val scheduleItemDataMapper: ScheduleItemDataModelToDomainMapper,
    private val saveScheduleItemMapper: SaveScheduleItemDomainModelToDataMapper
) : GetScheduleByMedicineRepository,
    SaveMedicineScheduleRepository,
    SaveScheduleItemRepository,
    GetSchedulesByPatientRepository {

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
        val saveModel = saveMedicineMapper
            .toData(medicineSchedule)
            .let { medicineScheduleDataSourceMapper.toDataSource(it) }

        val id = medicineScheduleDao.insert(saveModel)

        // Save Schedule Items
        medicineSchedule.schedules.forEach {
            saveScheduleItemOnly(it, id.toString())
        }

        val model = medicineScheduleDao.getById(id.toInt()).first()

        return medicineScheduleDataSourceMapper
            .toData(model)
            .let { medicineScheduleDataMapper.toDomain(it) }
    }

    override suspend fun saveScheduleItem(
        scheduleItem: SaveScheduleItemDomainModel,
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

    private suspend fun saveScheduleItemOnly(
        scheduleItem: SaveScheduleItemDomainModel,
        scheduleId: String
    ): String {
        val saveModel = saveScheduleItemMapper
            .toData(scheduleItem)
            .let { scheduleItemDataSourceMapper.toDataSource(it, scheduleId) }

        return scheduleItemDao.insert(saveModel).toString()
    }
}
