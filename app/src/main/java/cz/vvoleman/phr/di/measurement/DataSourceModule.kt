package cz.vvoleman.phr.di.measurement

import cz.vvoleman.phr.common.data.mapper.PatientDataSourceModelToDomainMapper
import cz.vvoleman.phr.common.data.mapper.problemCategory.ProblemCategoryDataSourceModelToDomainMapper
import cz.vvoleman.phr.data.PatientDatabase
import cz.vvoleman.phr.featureMeasurement.data.datasource.room.mapper.MeasurementGroupDataSourceModelToDataMapper
import cz.vvoleman.phr.featureMeasurement.data.datasource.room.mapper.MeasurementGroupEntryDataSourceModelToDataMapper
import cz.vvoleman.phr.featureMeasurement.data.datasource.room.mapper.MeasurementGroupScheduleItemDataSourceModelToDataMapper
import cz.vvoleman.phr.featureMeasurement.data.datasource.room.mapper.NumericFieldDataSourceModelToDataMapper
import cz.vvoleman.phr.featureMeasurement.data.datasource.room.mapper.UnitDataSourceModelToDataMapper
import cz.vvoleman.phr.featureMeasurement.data.datasource.room.mapper.UnitGroupDataSourceModelToDataMapper
import cz.vvoleman.phr.featureMeasurement.data.datasource.room.unit.UnitGroupDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {

    @Provides
    fun providesMeasurementGroupDao(db: PatientDatabase) = db.measurementGroupDao()

    @Provides
    fun providesMeasurementGroupScheduleItemDao(db: PatientDatabase) = db.measurementGroupScheduleItemDao()

    @Provides
    fun providesMeasurementGroupEntryDao(db: PatientDatabase) = db.measurementGroupEntryDao()

    @Provides
    fun providesUnitGroupDao(db: PatientDatabase) = db.unitGroupDao()

    @Provides
    fun providesNumericFieldTypeDao(db: PatientDatabase) = db.numericFieldTypeDao()

    @Provides
    fun providesUnitDataSourceModelToDataMapper() = UnitDataSourceModelToDataMapper()

    @Provides
    fun providesUnitGroupDataSourceModelToDataMapper(unitMapper: UnitDataSourceModelToDataMapper) =
        UnitGroupDataSourceModelToDataMapper(unitMapper)

    @Provides
    fun providesMeasurementGroupScheduleItemDataSourceModelToDataMapper() =
        MeasurementGroupScheduleItemDataSourceModelToDataMapper()

    @Provides
    fun providesNumericFieldDataSourceModelToDataMapper(
        unitGroupMapper: UnitGroupDataSourceModelToDataMapper,
        unitGroupDao: UnitGroupDao
    ) = NumericFieldDataSourceModelToDataMapper(unitGroupMapper, unitGroupDao)

    @Provides
    fun providesMeasurementGroupDataSourceModelToDataMapper(
        scheduleItemMapper: MeasurementGroupScheduleItemDataSourceModelToDataMapper,
        numericFieldMapper: NumericFieldDataSourceModelToDataMapper,
        patientMapper: PatientDataSourceModelToDomainMapper,
        entryMapper: MeasurementGroupEntryDataSourceModelToDataMapper,
        problemCategoryMapper: ProblemCategoryDataSourceModelToDomainMapper,
    ) = MeasurementGroupDataSourceModelToDataMapper(
        scheduleItemMapper = scheduleItemMapper,
        numericFieldMapper = numericFieldMapper,
        patientMapper = patientMapper,
        entryMapper = entryMapper,
        problemCategoryMapper = problemCategoryMapper,
    )

    @Provides
    fun providesMeasurementGroupEntryDataSourceModelToDataMapper() = MeasurementGroupEntryDataSourceModelToDataMapper()
}
