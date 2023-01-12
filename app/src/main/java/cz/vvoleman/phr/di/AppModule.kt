package cz.vvoleman.phr.di

import android.app.Application
import androidx.room.Room
import cz.vvoleman.phr.data.PatientDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(ActivityComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePatientDatabase(app: Application, callback: PatientDatabase.Callback) =
        Room.databaseBuilder(app, PatientDatabase::class.java, "patient_database")
            .fallbackToDestructiveMigration()
            .addCallback(callback)
            .build()

    @Provides
    fun providePatientDao(db: PatientDatabase) = db.patientDao()

    @Provides
    fun provideFacilityDao(db: PatientDatabase) = db.facilityDao()

    @Provides
    fun provideDiagnoseDao(db: PatientDatabase) = db.diagnoseDao()

    @Provides
    fun provideMedicalRecordDao(db: PatientDatabase) = db.medicalRecordDao()
}

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope