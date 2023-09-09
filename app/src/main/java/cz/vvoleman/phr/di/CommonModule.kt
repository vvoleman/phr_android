package cz.vvoleman.phr.di

import cz.vvoleman.phr.data.PatientDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class CommonModule {

    @Provides
    fun providesPatientDao(db: PatientDatabase) = db.patientDao()
}
