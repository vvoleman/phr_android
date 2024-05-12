package cz.vvoleman.phr.di.event

import cz.vvoleman.phr.common.data.mapper.PatientDataSourceModelToDomainMapper
import cz.vvoleman.phr.common.data.mapper.healthcare.SpecificMedicalWorkerDataSourceToDomainMapper
import cz.vvoleman.phr.data.PatientDatabase
import cz.vvoleman.phr.featureEvent.data.datasource.room.mapper.EventDataSourceModelToDataMapper
import cz.vvoleman.phr.featureEvent.data.datasource.room.mapper.EventDataSourceModelToSaveDomainMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {

    @Provides
    fun providesEventDao(db: PatientDatabase) = db.eventDao()

    @Provides
    fun providesEventDataSourceModelToDataMapper(
        patientMapper: PatientDataSourceModelToDomainMapper,
        workerMapper: SpecificMedicalWorkerDataSourceToDomainMapper
    ) = EventDataSourceModelToDataMapper(patientMapper, workerMapper)

    @Provides
    fun providesEventDataSourceModelToSaveDomainMapper() = EventDataSourceModelToSaveDomainMapper()
}
