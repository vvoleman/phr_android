package cz.vvoleman.phr.di.event

import cz.vvoleman.phr.data.PatientDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {

    @Provides
    fun providesEventDao(db: PatientDatabase) = db.eventDao()

}
