package cz.vvoleman.phr.di.measurement

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

}
