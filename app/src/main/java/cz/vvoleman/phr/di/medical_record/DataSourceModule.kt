package cz.vvoleman.phr.di.medical_record

import cz.vvoleman.phr.data.PatientDatabase
import cz.vvoleman.phr.feature_medicalrecord.data.datasource.model.room.MedicalRecordDao
import cz.vvoleman.phr.feature_medicalrecord.data.datasource.model.room.worker.MedicalWorkerDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {

    @Provides
    fun providesMedicalRecordDao(db: PatientDatabase): MedicalRecordDao = db.medicalRecordDao()

    @Provides
    fun providesMedicalWorkerDao(db: PatientDatabase): MedicalWorkerDao = db.medicalWorkerDao()

    @Provides
    fun providesDiagnoseDao(db: PatientDatabase) = db.diagnoseDao()

    @Provides
    fun providesDiagnoseGroup(db: PatientDatabase) = db.diagnoseGroupDao()

    @Provides
    fun providesProblemCategoryDao(db: PatientDatabase) = db.problemCategoryDao()

    @Provides
    fun providesMedicalRecordAssetDao(db: PatientDatabase) = db.medicalRecordAssetDao()


}