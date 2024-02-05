package cz.vvoleman.phr.di.medicine

import cz.vvoleman.phr.common.data.alarm.AlarmScheduler
import cz.vvoleman.phr.featureMedicine.data.alarm.MedicineAlarmManager
import cz.vvoleman.phr.featureMedicine.data.datasource.retrofit.BackendApi
import cz.vvoleman.phr.featureMedicine.data.datasource.retrofit.medicine.mapper.MedicineApiDataSourceModelToDataMapper
import cz.vvoleman.phr.featureMedicine.data.datasource.room.medicine.dao.*
import cz.vvoleman.phr.featureMedicine.data.datasource.room.medicine.mapper.MedicineDataSourceModelToDataMapper
import cz.vvoleman.phr.featureMedicine.data.datasource.room.medicine.mapper.ProductFormDataSourceModelToDataMapper
import cz.vvoleman.phr.featureMedicine.data.datasource.room.medicine.mapper.SubstanceDataSourceModelToDataMapper
import cz.vvoleman.phr.featureMedicine.data.datasource.room.schedule.dao.MedicineScheduleDao
import cz.vvoleman.phr.featureMedicine.data.datasource.room.schedule.dao.ScheduleItemDao
import cz.vvoleman.phr.featureMedicine.data.datasource.room.schedule.mapper.MedicineScheduleDataSourceModelToDataMapper
import cz.vvoleman.phr.featureMedicine.data.datasource.room.schedule.mapper.SaveMedicineScheduleDataSourceModelToDataMapper
import cz.vvoleman.phr.featureMedicine.data.datasource.room.schedule.mapper.ScheduleItemDataSourceModelToDataMapper
import cz.vvoleman.phr.featureMedicine.data.mapper.*
import cz.vvoleman.phr.featureMedicine.data.mapper.medicine.*
import cz.vvoleman.phr.featureMedicine.data.mapper.schedule.MedicineScheduleDataModelToDomainMapper
import cz.vvoleman.phr.featureMedicine.data.mapper.schedule.SaveMedicineScheduleDomainModelToDataMapper
import cz.vvoleman.phr.featureMedicine.data.mapper.schedule.SaveScheduleItemDomainModelToDataMapper
import cz.vvoleman.phr.featureMedicine.data.mapper.schedule.ScheduleItemDataModelToDomainMapper
import cz.vvoleman.phr.featureMedicine.data.repository.AlarmRepository
import cz.vvoleman.phr.featureMedicine.data.repository.MedicineRepository
import cz.vvoleman.phr.featureMedicine.data.repository.SchedulesRepository
import cz.vvoleman.phr.featureMedicine.domain.repository.ChangeMedicineScheduleAlarmEnabledRepository
import cz.vvoleman.phr.featureMedicine.domain.repository.DeleteMedicineScheduleRepository
import cz.vvoleman.phr.featureMedicine.domain.repository.DeleteScheduleAlarmRepository
import cz.vvoleman.phr.featureMedicine.domain.repository.GetMedicineByIdRepository
import cz.vvoleman.phr.featureMedicine.domain.repository.GetMedicineScheduleByIdRepository
import cz.vvoleman.phr.featureMedicine.domain.repository.GetSchedulesByMedicineRepository
import cz.vvoleman.phr.featureMedicine.domain.repository.SaveMedicineScheduleRepository
import cz.vvoleman.phr.featureMedicine.domain.repository.SaveScheduleItemRepository
import cz.vvoleman.phr.featureMedicine.domain.repository.ScheduleMedicineRepository
import cz.vvoleman.phr.featureMedicine.domain.repository.SearchMedicineRepository
import cz.vvoleman.phr.featureMedicine.domain.repository.timeline.GetSchedulesByPatientRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    fun providesMedicineRepository(
        backendApi: BackendApi,
        medicineDao: MedicineDao,
        substanceDao: SubstanceDao,
        substanceDataMapper: SubstanceDataModelToDomainMapper,
        substanceDataSourceMapper: SubstanceDataSourceModelToDataMapper,
        productFormDao: ProductFormDao,
        productFormDataMapper: ProductFormDataModelToDomainMapper,
        productFormDataSourceMapper: ProductFormDataSourceModelToDataMapper,
        medicineApiMapper: MedicineApiDataSourceModelToDataMapper,
        medicineDataMapper: MedicineDataModelToDomainMapper,
        medicineDataSourceMapper: MedicineDataSourceModelToDataMapper
    ) = MedicineRepository(
        backendApi,
        medicineDao,
        substanceDao,
        productFormDao,
        medicineApiMapper,
        medicineDataMapper,
        medicineDataSourceMapper,
        substanceDataMapper,
        substanceDataSourceMapper,
        productFormDataMapper,
        productFormDataSourceMapper
    )

    @Provides
    fun providesScheduleRepository(
        medicineScheduleDao: MedicineScheduleDao,
        medicineScheduleDataSourceMapper: MedicineScheduleDataSourceModelToDataMapper,
        medicineScheduleDataMapper: MedicineScheduleDataModelToDomainMapper,
        saveMedicineMapper: SaveMedicineScheduleDomainModelToDataMapper,
        saveMedicineDataSourceMapper: SaveMedicineScheduleDataSourceModelToDataMapper,
        scheduleItemDao: ScheduleItemDao,
        scheduleItemDataSourceMapper: ScheduleItemDataSourceModelToDataMapper,
        scheduleItemDataMapper: ScheduleItemDataModelToDomainMapper,
    ) = SchedulesRepository(
        medicineScheduleDao,
        medicineScheduleDataSourceMapper,
        medicineScheduleDataMapper,
        saveMedicineMapper,
        saveMedicineDataSourceMapper,
        scheduleItemDao,
        scheduleItemDataSourceMapper,
        scheduleItemDataMapper,
    )

    @Provides
    fun providesGetScheduleByMedicineRepository(
        scheduleRepository: SchedulesRepository
    ): GetSchedulesByMedicineRepository = scheduleRepository

    @Provides
    fun providesSaveMedicineScheduleRepository(
        scheduleRepository: SchedulesRepository
    ): SaveMedicineScheduleRepository = scheduleRepository

    @Provides
    fun providesSaveScheduleItemRepository(
        scheduleRepository: SchedulesRepository
    ): SaveScheduleItemRepository = scheduleRepository

    @Provides
    fun providesGetSchedulesByPatientRepository(
        scheduleRepository: SchedulesRepository
    ): GetSchedulesByPatientRepository = scheduleRepository

    @Provides
    fun providesSearchMedicineRepository(
        medicineRepository: MedicineRepository
    ): SearchMedicineRepository = medicineRepository

    @Provides
    fun providesGetMedicineScheduleByIdRepository(
        scheduleRepository: SchedulesRepository
    ): GetMedicineScheduleByIdRepository = scheduleRepository

    @Provides
    fun providesDeleteMedicineScheduleRepository(
        scheduleRepository: SchedulesRepository
    ): DeleteMedicineScheduleRepository = scheduleRepository

    @Provides
    fun providesSubstanceDataModelToDomainMapper() = SubstanceDataModelToDomainMapper()

    @Provides
    fun providesSubstanceAmountDataModelToDomainMapper(
        substanceDataModelToDomainMapper: SubstanceDataModelToDomainMapper
    ) = SubstanceAmountDataModelToDomainMapper(substanceDataModelToDomainMapper)

    @Provides
    fun providesProductFormDataModelToDomainMapper() = ProductFormDataModelToDomainMapper()

    @Provides
    fun providesPackagingDataModelToDomainMapper(
        productFormDataModelToDomainMapper: ProductFormDataModelToDomainMapper
    ) = PackagingDataModelToDomainMapper(productFormDataModelToDomainMapper)

    @Provides
    fun providesMedicineDataSourceModelToDomainMapper(
        packagingDataModelToDomainMapper: PackagingDataModelToDomainMapper,
        substanceAmountDataModelToDomainMapper: SubstanceAmountDataModelToDomainMapper
    ) = MedicineDataModelToDomainMapper(
        packagingDataModelToDomainMapper,
        substanceAmountDataModelToDomainMapper
    )

    @Provides
    fun providesScheduleItemDataModelToDomainMapper() = ScheduleItemDataModelToDomainMapper()

    @Provides
    fun providesMedicineScheduleDataModelToDomainMapper(
        scheduleItemDataModelToDomainMapper: ScheduleItemDataModelToDomainMapper,
        medicineDataModelToDomainMapper: MedicineDataModelToDomainMapper
    ) = MedicineScheduleDataModelToDomainMapper(
        scheduleItemDataModelToDomainMapper,
        medicineDataModelToDomainMapper
    )

    @Provides
    fun providesSaveScheduleItemDomainModelToDataMapper() = SaveScheduleItemDomainModelToDataMapper()

    @Provides
    fun providesSaveMedicineScheduleDomainModelToDataMapper(
        scheduleItemDomainModelToDataMapper: ScheduleItemDataModelToDomainMapper,
        medicineDataModelToDomainMapper: MedicineDataModelToDomainMapper
    ) = SaveMedicineScheduleDomainModelToDataMapper(
        medicineDataModelToDomainMapper,
        scheduleItemDomainModelToDataMapper
    )

    @Provides
    fun providesGetMedicineByIdRepository(
        medicineRepository: MedicineRepository
    ): GetMedicineByIdRepository = medicineRepository

    @Provides
    fun providesAlarmRepository(
        medicineScheduleDao: MedicineScheduleDao,
        medicineScheduleDataMapper: MedicineScheduleDataSourceModelToDataMapper,
        medicineScheduleMapper: MedicineScheduleDataModelToDomainMapper,
        alarmScheduler: AlarmScheduler,
    ) = AlarmRepository(
        medicineScheduleDao,
        medicineScheduleDataMapper,
        medicineScheduleMapper,
        alarmScheduler
    )

    @Provides
    fun providesScheduleMedicineRepository(
        alarmRepository: AlarmRepository
    ): ScheduleMedicineRepository = alarmRepository

    @Provides
    fun providesDeleteScheduleAlarmRepository(
        alarmRepository: AlarmRepository
    ): DeleteScheduleAlarmRepository = alarmRepository

    @Provides
    fun providesMedicineAlarmManager(
        alarmRepository: AlarmRepository
    ) = MedicineAlarmManager(alarmRepository)

    @Provides
    fun providesChangeMedicineScheduleAlarmEnabledRepository(
        scheduleRepository: SchedulesRepository
    ): ChangeMedicineScheduleAlarmEnabledRepository = scheduleRepository
}
