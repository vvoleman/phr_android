package cz.vvoleman.phr.di.medicine

import cz.vvoleman.phr.featureMedicine.data.datasource.retrofit.BackendApi
import cz.vvoleman.phr.featureMedicine.data.datasource.retrofit.medicine.mapper.MedicineApiDataSourceModelToDataMapper
import cz.vvoleman.phr.featureMedicine.data.datasource.room.medicine.dao.*
import cz.vvoleman.phr.featureMedicine.data.datasource.room.medicine.mapper.MedicineDataSourceModelToDataMapper
import cz.vvoleman.phr.featureMedicine.data.datasource.room.medicine.mapper.ProductFormDataSourceModelToDataMapper
import cz.vvoleman.phr.featureMedicine.data.datasource.room.medicine.mapper.SubstanceDataSourceModelToDataMapper
import cz.vvoleman.phr.featureMedicine.data.datasource.room.schedule.dao.MedicineScheduleDao
import cz.vvoleman.phr.featureMedicine.data.datasource.room.schedule.dao.ScheduleItemDao
import cz.vvoleman.phr.featureMedicine.data.datasource.room.schedule.mapper.MedicineScheduleDataSourceModelToDataMapper
import cz.vvoleman.phr.featureMedicine.data.datasource.room.schedule.mapper.ScheduleItemDataSourceModelToDataMapper
import cz.vvoleman.phr.featureMedicine.data.mapper.*
import cz.vvoleman.phr.featureMedicine.data.mapper.medicine.*
import cz.vvoleman.phr.featureMedicine.data.mapper.schedule.MedicineScheduleDataModelToDomainMapper
import cz.vvoleman.phr.featureMedicine.data.mapper.schedule.SaveMedicineScheduleDomainModelToDataMapper
import cz.vvoleman.phr.featureMedicine.data.mapper.schedule.SaveScheduleItemDomainModelToDataMapper
import cz.vvoleman.phr.featureMedicine.data.mapper.schedule.ScheduleItemDataModelToDomainMapper
import cz.vvoleman.phr.featureMedicine.data.repository.MedicineRepository
import cz.vvoleman.phr.featureMedicine.data.repository.ScheduleRepository
import cz.vvoleman.phr.featureMedicine.domain.repository.GetScheduleByMedicineRepository
import cz.vvoleman.phr.featureMedicine.domain.repository.SaveMedicineScheduleRepository
import cz.vvoleman.phr.featureMedicine.domain.repository.SaveScheduleItemRepository
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
        scheduleItemDao: ScheduleItemDao,
        scheduleItemDataSourceMapper: ScheduleItemDataSourceModelToDataMapper,
        scheduleItemDataMapper: ScheduleItemDataModelToDomainMapper,
        saveScheduleItemMapper: SaveScheduleItemDomainModelToDataMapper
    ) = ScheduleRepository(
        medicineScheduleDao,
        medicineScheduleDataSourceMapper,
        medicineScheduleDataMapper,
        saveMedicineMapper,
        scheduleItemDao,
        scheduleItemDataSourceMapper,
        scheduleItemDataMapper,
        saveScheduleItemMapper
    )

    @Provides
    fun providesGetScheduleByMedicineRepository(
        scheduleRepository: ScheduleRepository
    ): GetScheduleByMedicineRepository = scheduleRepository

    @Provides
    fun providesSaveMedicineScheduleRepository(
        scheduleRepository: ScheduleRepository
    ): SaveMedicineScheduleRepository = scheduleRepository

    @Provides
    fun providesSaveScheduleItemRepository(
        scheduleRepository: ScheduleRepository
    ): SaveScheduleItemRepository = scheduleRepository

    @Provides
    fun providesGetSchedulesByPatientRepository(
        scheduleRepository: ScheduleRepository
    ): GetSchedulesByPatientRepository = scheduleRepository

    @Provides
    fun providesSearchMedicineRepository(
        medicineRepository: MedicineRepository
    ): SearchMedicineRepository = medicineRepository

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
        saveScheduleItemDomainModelToDataMapper: SaveScheduleItemDomainModelToDataMapper,
        medicineDataModelToDomainMapper: MedicineDataModelToDomainMapper
    ) = SaveMedicineScheduleDomainModelToDataMapper(
        medicineDataModelToDomainMapper,
        saveScheduleItemDomainModelToDataMapper
    )
}
