package cz.vvoleman.phr.di.common

import cz.vvoleman.phr.common.data.datasource.model.healthcare.worker.MedicalWorkerDao
import cz.vvoleman.phr.common.data.datasource.model.retrofit.healthcare.HealthcareApi
import cz.vvoleman.phr.data.PatientDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {

    @Provides
    fun providesMedicalWorkerDao(db: PatientDatabase): MedicalWorkerDao = db.medicalWorkerDao()

    @Provides
    fun providesMedicalServiceDao(db: PatientDatabase) = db.medicalServiceDao()

    @Provides
    fun providesMedicalFacilityDao(db: PatientDatabase) = db.medicalFacilityDao()

    @Provides
    fun providesSpecificMedicalWorkerDao(db: PatientDatabase) = db.specificMedicalWorkerDao()

    @Provides
    fun providesHealthcareApi(
        retrofit: Retrofit
    ): HealthcareApi = retrofit.create(HealthcareApi::class.java)
}
