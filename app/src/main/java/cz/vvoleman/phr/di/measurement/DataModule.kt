package cz.vvoleman.phr.di.measurement

import cz.vvoleman.featureMeasurement.data.datasource.room.MeasurementGroupDao
import cz.vvoleman.featureMeasurement.data.datasource.room.MeasurementGroupScheduleItemDao
import cz.vvoleman.featureMeasurement.data.datasource.room.mapper.MeasurementGroupDataSourceModelToDataMapper
import cz.vvoleman.featureMeasurement.data.datasource.room.mapper.MeasurementGroupScheduleItemDataSourceModelToDataMapper
import cz.vvoleman.featureMeasurement.data.datasource.room.mapper.UnitGroupDataSourceModelToDataMapper
import cz.vvoleman.featureMeasurement.data.datasource.room.unit.UnitGroupDao
import cz.vvoleman.featureMeasurement.data.mapper.addEdit.SaveMeasurementGroupDataModelToDomainMapper
import cz.vvoleman.featureMeasurement.data.mapper.addEdit.SaveMeasurementGroupScheduleItemDataModelToDomainMapper
import cz.vvoleman.featureMeasurement.data.mapper.core.MeasurementGroupDataModelToDomainMapper
import cz.vvoleman.featureMeasurement.data.mapper.core.MeasurementGroupEntryDataModelToDomainMapper
import cz.vvoleman.featureMeasurement.data.mapper.core.MeasurementGroupFieldDataToDomainMapper
import cz.vvoleman.featureMeasurement.data.mapper.core.MeasurementGroupScheduleItemDataModelToDomainMapper
import cz.vvoleman.featureMeasurement.data.mapper.core.NumericFieldDataModelToDomainMapper
import cz.vvoleman.featureMeasurement.data.mapper.core.UnitDataModelToDomainMapper
import cz.vvoleman.featureMeasurement.data.mapper.core.UnitGroupDataModelToDomainMapper
import cz.vvoleman.featureMeasurement.data.repository.MeasurementGroupRepository
import cz.vvoleman.featureMeasurement.data.repository.UnitRepository
import cz.vvoleman.featureMeasurement.domain.repository.GetMeasurementGroupRepository
import cz.vvoleman.featureMeasurement.domain.repository.GetUnitGroupsRepository
import cz.vvoleman.featureMeasurement.domain.repository.SaveMeasurementGroupRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    fun providesUnitDataModelToDomainMapper(): UnitDataModelToDomainMapper {
        return UnitDataModelToDomainMapper()
    }

    @Provides
    fun providesUnitGroupDataModelToDomainMapper(unitMapper: UnitDataModelToDomainMapper): UnitGroupDataModelToDomainMapper {
        return UnitGroupDataModelToDomainMapper(unitMapper)
    }

    @Provides
    fun providesNumericFieldDataModelToDomainMapper(
        unitGroupMapper: UnitGroupDataModelToDomainMapper
    ): NumericFieldDataModelToDomainMapper {
        return NumericFieldDataModelToDomainMapper(unitGroupMapper)
    }

    @Provides
    fun providesMeasurementGroupScheduleItemDataModelToDomainMapper(
    ): MeasurementGroupScheduleItemDataModelToDomainMapper {
        return MeasurementGroupScheduleItemDataModelToDomainMapper()
    }

    @Provides
    fun providesMeasurementGroupDataModelToDomainMapper(
        fieldMapper: MeasurementGroupFieldDataToDomainMapper,
        scheduleItemMapper: MeasurementGroupScheduleItemDataModelToDomainMapper,
        entryMapper: MeasurementGroupEntryDataModelToDomainMapper
    ): MeasurementGroupDataModelToDomainMapper {
        return MeasurementGroupDataModelToDomainMapper(
            fieldMapper,
            scheduleItemMapper,
            entryMapper
        )
    }

    @Provides
    fun providesMeasurementGroupEntryDataModelToDomainMapper(): MeasurementGroupEntryDataModelToDomainMapper {
        return MeasurementGroupEntryDataModelToDomainMapper()
    }

    @Provides
    fun providesMeasurementGroupRepository(
        saveMeasurementGroupMapper: SaveMeasurementGroupDataModelToDomainMapper,
        measurementGroupDao: MeasurementGroupDao,
        groupDataSourceMapper: MeasurementGroupDataSourceModelToDataMapper,
        groupDataMapper: MeasurementGroupDataModelToDomainMapper,
        saveScheduleItemMapper: SaveMeasurementGroupScheduleItemDataModelToDomainMapper,
        scheduleItemDataSource: MeasurementGroupScheduleItemDataSourceModelToDataMapper,
        scheduleItemDao: MeasurementGroupScheduleItemDao,
    ) = MeasurementGroupRepository(
        saveMeasurementGroupMapper = saveMeasurementGroupMapper,
        measurementGroupDao = measurementGroupDao,
        measurementGroupDataSourceMapper = groupDataSourceMapper,
        measurementGroupDataMapper = groupDataMapper,
        saveScheduleItemMapper = saveScheduleItemMapper,
        scheduleItemDataSourceMapper = scheduleItemDataSource,
        scheduleItemDao = scheduleItemDao,
    )

    @Provides
    fun providesGetMeasurementGroupRepository(
        measurementGroupRepository: MeasurementGroupRepository
    ): GetMeasurementGroupRepository = measurementGroupRepository

    @Provides
    fun providesUnitRepository(
        unitGroupDao: UnitGroupDao,
        unitGroupDataSourceMapper: UnitGroupDataSourceModelToDataMapper,
        unitGroupDataMapper: UnitGroupDataModelToDomainMapper
    ) = UnitRepository(
        unitGroupDao = unitGroupDao,
        unitGroupDataSourceMapper = unitGroupDataSourceMapper,
        unitGroupDataMapper = unitGroupDataMapper
    )

    @Provides
    fun providesGetUnitGroupsRepository(
        unitRepository: UnitRepository
    ): GetUnitGroupsRepository = unitRepository

    @Provides
    fun providesMeasurementGroupFieldDataToDomainMapper(
        numericMapper: NumericFieldDataModelToDomainMapper,
    ) = MeasurementGroupFieldDataToDomainMapper(
        numericMapper,
    )

    @Provides
    fun providesSaveMeasurementGroupDataModelToDomainMapper(
        fieldMapper: MeasurementGroupFieldDataToDomainMapper,
        saveScheduleItemMapper: SaveMeasurementGroupScheduleItemDataModelToDomainMapper,
    ) = SaveMeasurementGroupDataModelToDomainMapper(
        fieldMapper,
        saveScheduleItemMapper,
    )

    @Provides
    fun providesSaveMeasurementGroupScheduleItemDataModelToDomainMapper() =
        SaveMeasurementGroupScheduleItemDataModelToDomainMapper()

    @Provides
    fun providesSaveMeasurementGroupRepository(
        measurementGroupRepository: MeasurementGroupRepository,
    ): SaveMeasurementGroupRepository = measurementGroupRepository

}
