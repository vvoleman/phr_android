package cz.vvoleman.phr.di.measurement

import cz.vvoleman.featureMeasurement.data.datasource.room.mapper.MeasurementGroupDataSourceModelToDataMapper
import cz.vvoleman.featureMeasurement.data.datasource.room.mapper.MeasurementGroupScheduleItemDataSourceModelToDataMapper
import cz.vvoleman.featureMeasurement.data.datasource.room.mapper.NumericFieldDataSourceModelToDataMapper
import cz.vvoleman.featureMeasurement.data.datasource.room.mapper.UnitDataSourceModelToDataMapper
import cz.vvoleman.featureMeasurement.data.datasource.room.mapper.UnitGroupDataSourceModelToDataMapper
import cz.vvoleman.featureMeasurement.data.datasource.room.unit.UnitGroupDao
import cz.vvoleman.phr.common.data.mapper.PatientDataSourceModelToDomainMapper
import cz.vvoleman.phr.data.PatientDatabase
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
        numericFieldMapper: NumericFieldDataSourceModelToDataMapper,
        patientMapper: PatientDataSourceModelToDomainMapper
    ) = MeasurementGroupDataSourceModelToDataMapper(numericFieldMapper, patientMapper)
}
