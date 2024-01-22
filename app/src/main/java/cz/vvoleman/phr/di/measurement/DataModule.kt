package cz.vvoleman.phr.di.measurement

import cz.vvoleman.featureMeasurement.data.datasource.room.MeasurementGroupDao
import cz.vvoleman.featureMeasurement.data.datasource.room.mapper.MeasurementGroupDataSourceModelToDataMapper
import cz.vvoleman.featureMeasurement.data.datasource.room.mapper.UnitGroupDataSourceModelToDataMapper
import cz.vvoleman.featureMeasurement.data.datasource.room.unit.UnitGroupDao
import cz.vvoleman.featureMeasurement.data.mapper.MeasurementGroupDataModelToDomainMapper
import cz.vvoleman.featureMeasurement.data.mapper.MeasurementGroupEntryDataModelToDomainMapper
import cz.vvoleman.featureMeasurement.data.mapper.MeasurementGroupFieldDataToDomainMapper
import cz.vvoleman.featureMeasurement.data.mapper.MeasurementGroupScheduleItemDataModelToDomainMapper
import cz.vvoleman.featureMeasurement.data.mapper.NumericFieldDataModelToDomainMapper
import cz.vvoleman.featureMeasurement.data.mapper.UnitDataModelToDomainMapper
import cz.vvoleman.featureMeasurement.data.mapper.UnitGroupDataModelToDomainMapper
import cz.vvoleman.featureMeasurement.data.repository.MeasurementGroupRepository
import cz.vvoleman.featureMeasurement.data.repository.UnitRepository
import cz.vvoleman.featureMeasurement.domain.repository.GetMeasurementGroupRepository
import cz.vvoleman.featureMeasurement.domain.repository.GetUnitGroupsRepository
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
        measurementGroupDao: MeasurementGroupDao,
        groupDataSourceMapper: MeasurementGroupDataSourceModelToDataMapper,
        groupDataMapper: MeasurementGroupDataModelToDomainMapper,
    ) = MeasurementGroupRepository(
        measurementGroupDao = measurementGroupDao,
        measurementGroupDataSourceMapper = groupDataSourceMapper,
        measurementGroupDataMapper = groupDataMapper
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

}
