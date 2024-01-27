package cz.vvoleman.phr.di.measurement

import cz.vvoleman.phr.featureMeasurement.presentation.mapper.core.MeasurementGroupEntryPresentationModelToDomainMapper
import cz.vvoleman.phr.featureMeasurement.presentation.mapper.core.MeasurementGroupFieldPresentationToDomainMapper
import cz.vvoleman.phr.featureMeasurement.presentation.mapper.core.MeasurementGroupPresentationModelToDomainMapper
import cz.vvoleman.phr.featureMeasurement.presentation.mapper.core.MeasurementGroupScheduleItemPresentationModelToDomainMapper
import cz.vvoleman.phr.featureMeasurement.presentation.mapper.core.NumericFieldPresentationModelToDomainMapper
import cz.vvoleman.phr.featureMeasurement.presentation.mapper.core.UnitGroupPresentationModelToDomainMapper
import cz.vvoleman.phr.featureMeasurement.presentation.mapper.core.UnitPresentationModelToDomainMapper
import cz.vvoleman.phr.common.presentation.mapper.PatientPresentationModelToDomainMapper
import cz.vvoleman.phr.featureMeasurement.presentation.mapper.addEditEntry.EntryFieldPresentationModelToDomainMapper
import cz.vvoleman.phr.featureMeasurement.presentation.mapper.core.ScheduledMeasurementGroupPresentationModelToDomainMapper
import cz.vvoleman.phr.featureMeasurement.presentation.mapper.list.MeasurementGroupPresentationModelToNextScheduleMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class PresentationModule {

    @Provides
    fun provideNumericFieldPresentationModelToDomainMapper(
        unitGroupMapper: UnitGroupPresentationModelToDomainMapper,
    ): NumericFieldPresentationModelToDomainMapper {
        return NumericFieldPresentationModelToDomainMapper(
            unitGroupMapper = unitGroupMapper,
        )
    }

    @Provides
    fun provideUnitGroupPresentationModelToDomainMapper(
        unitMapper: UnitPresentationModelToDomainMapper,
    ): UnitGroupPresentationModelToDomainMapper {
        return UnitGroupPresentationModelToDomainMapper(
            unitMapper = unitMapper,
        )
    }

    @Provides
    fun provideUnitPresentationModelToDomainMapper(): UnitPresentationModelToDomainMapper {
        return UnitPresentationModelToDomainMapper()
    }

    @Provides
    fun provideMeasurementGroupScheduleItemPresentationModelToDomainMapper(): MeasurementGroupScheduleItemPresentationModelToDomainMapper {
        return MeasurementGroupScheduleItemPresentationModelToDomainMapper()
    }

    @Provides
    fun provideMeasurementGroupEntryPresentationModelToDomainMapper(): MeasurementGroupEntryPresentationModelToDomainMapper {
        return MeasurementGroupEntryPresentationModelToDomainMapper()
    }

    @Provides
    fun provideMeasurementGroupPresentationModelToDomainMapper(
        scheduleItemMapper: MeasurementGroupScheduleItemPresentationModelToDomainMapper,
        entryMapper: MeasurementGroupEntryPresentationModelToDomainMapper,
        fieldMapper: MeasurementGroupFieldPresentationToDomainMapper,
        patientMapper: PatientPresentationModelToDomainMapper,
    ): MeasurementGroupPresentationModelToDomainMapper {
        return MeasurementGroupPresentationModelToDomainMapper(
            scheduleItemMapper = scheduleItemMapper,
            entryMapper = entryMapper,
            fieldMapper = fieldMapper,
            patientMapper = patientMapper
        )
    }

    @Provides
    fun providesMeasurementGroupFieldPresentationToDomainMapper(
        numericMapper: NumericFieldPresentationModelToDomainMapper,
    ): MeasurementGroupFieldPresentationToDomainMapper {
        return MeasurementGroupFieldPresentationToDomainMapper(
            numericMapper = numericMapper,
        )
    }

    @Provides
    fun providesScheduledMeasurementGroupPresentationModelToDomainMapper(
        measurementGroupMapper: MeasurementGroupPresentationModelToDomainMapper
    ): ScheduledMeasurementGroupPresentationModelToDomainMapper {
        return ScheduledMeasurementGroupPresentationModelToDomainMapper(
            measurementGroupMapper = measurementGroupMapper
        )
    }

    @Provides
    fun providesMeasurementGroupPresentationModelToNextScheduleMapper() =
        MeasurementGroupPresentationModelToNextScheduleMapper()

    @Provides
    fun providesEntryFieldPresentationModelToDomainMapper() =
        EntryFieldPresentationModelToDomainMapper()

}
