package cz.vvoleman.phr.di.measurement

import cz.vvoleman.featureMeasurement.data.mapper.MeasurementGroupDataModelToDomainMapper
import cz.vvoleman.featureMeasurement.data.mapper.MeasurementGroupEntryDataModelToDomainMapper
import cz.vvoleman.featureMeasurement.data.mapper.MeasurementGroupScheduleItemDataModelToDomainMapper
import cz.vvoleman.featureMeasurement.data.mapper.NumericFieldDataModelToDomainMapper
import cz.vvoleman.featureMeasurement.data.mapper.UnitDataModelToDomainMapper
import cz.vvoleman.featureMeasurement.data.mapper.UnitGroupDataModelToDomainMapper
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
        numericFieldMapper: NumericFieldDataModelToDomainMapper,
        scheduleItemMapper: MeasurementGroupScheduleItemDataModelToDomainMapper,
        entryMapper: MeasurementGroupEntryDataModelToDomainMapper
    ): MeasurementGroupDataModelToDomainMapper {
        return MeasurementGroupDataModelToDomainMapper(
            numericFieldMapper,
            scheduleItemMapper,
            entryMapper
        )
    }

    @Provides
    fun providesMeasurementGroupEntryDataModelToDomainMapper(): MeasurementGroupEntryDataModelToDomainMapper {
        return MeasurementGroupEntryDataModelToDomainMapper()
    }



}
