package cz.vvoleman.phr.di.medicine

import cz.vvoleman.phr.data.PatientDatabase
import cz.vvoleman.phr.feature_medicine.data.datasource.retrofit.BackendApi
import cz.vvoleman.phr.feature_medicine.data.mapper.*
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
    fun providesMedicineDao(db: PatientDatabase) = db.medicineDao()

    @Provides
    fun providesPackagingDao(db: PatientDatabase) = db.packagingDao()

    @Provides
    fun providesProductFormDao(db: PatientDatabase) = db.productFormDao()

    @Provides
    fun providesSubstanceAmountDao(db: PatientDatabase) = db.substanceAmountDao()

    @Provides
    fun providesSubstanceDao(db: PatientDatabase) = db.substanceDao()

    @Provides
    @Singleton
    fun provideBackendApi(retrofit: Retrofit): BackendApi =
        retrofit
            .create(BackendApi::class.java)

    @Provides
    fun providesPackagingApiModelToDbMapper() = PackagingApiModelToDbMapper()

    @Provides
    fun providesSubstanceApiModelToDbMapper() = SubstanceApiModelToDbMapper()

    @Provides
    fun providesMedicineApiModelToDbMapper(
        substanceApiModelToDbMapper: SubstanceApiModelToDbMapper,
        packagingApiModelToDbMapper: PackagingApiModelToDbMapper,
    ) = MedicineApiModelToDbMapper(
        substanceApiModelToDbMapper,
        packagingApiModelToDbMapper,
    )

    @Provides
    fun providesSubstanceDataSourceModelToDomainMapper() = SubstanceDataSourceModelToDomainMapper()

    @Provides
    fun providesPackagingDataSourceModelToDomainMapper() = PackagingDataSourceModelToDomainMapper()

    @Provides
    fun providesMedicineDataSourceModelToDomainMapper(
        substanceDataSourceModelToDomainMapper: SubstanceDataSourceModelToDomainMapper,
        packagingDataSourceModelToDomainMapper: PackagingDataSourceModelToDomainMapper,
    ) = MedicineDataSourceModelToDomainMapper(
        substanceDataSourceModelToDomainMapper,
        packagingDataSourceModelToDomainMapper,
    )

}