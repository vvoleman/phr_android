package cz.vvoleman.phr.di.medicalRecord

import cz.vvoleman.phr.data.PatientDatabase
import cz.vvoleman.phr.featureMedicalRecord.data.datasource.model.retrofit.BackendApi
import cz.vvoleman.phr.featureMedicalRecord.data.datasource.model.room.MedicalRecordDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {

    @Provides
    fun providesMedicalRecordDao(db: PatientDatabase): MedicalRecordDao = db.medicalRecordDao()

    @Provides
    fun providesDiagnoseDao(db: PatientDatabase) = db.diagnoseDao()

    @Provides
    fun providesDiagnoseGroup(db: PatientDatabase) = db.diagnoseGroupDao()

    @Provides
    fun providesMedicalRecordAssetDao(db: PatientDatabase) = db.medicalRecordAssetDao()

    @Provides
    @Singleton
    fun provideBackendApi(retrofit: Retrofit): BackendApi =
        retrofit
            .create(BackendApi::class.java)
}
