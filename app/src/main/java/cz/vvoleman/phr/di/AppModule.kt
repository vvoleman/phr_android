package cz.vvoleman.phr.di

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import androidx.room.Room
import cz.vvoleman.phr.api.backend.BackendApi
import cz.vvoleman.phr.data.PatientDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
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
    fun provideDiagnoseGroup(db: PatientDatabase) = db.diagnoseGroupDao()

    @Provides
    fun provideMedicineDao(db: PatientDatabase) = db.medicineDao()

    @Provides
    fun provideSubstanceDao(db: PatientDatabase) = db.substanceDao()

    @Provides
    fun provideMedicalRecordDao(db: PatientDatabase) = db.medicalRecordDao()

    @Provides
    fun provideConnectivityManager(@ApplicationContext context: Context) = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    @Provides
    @Singleton
    fun provideRetroFit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(BackendApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideBackendApi(retrofit: Retrofit): BackendApi =
        retrofit
            .create(BackendApi::class.java)

    @ApplicationScope
    @Provides
    fun provideApplicationScope() = CoroutineScope(SupervisorJob())
}

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope