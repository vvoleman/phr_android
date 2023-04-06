package cz.vvoleman.phr.di.medicine

import cz.vvoleman.phr.data.PatientDatabase
import cz.vvoleman.phr.feature_medicine.data.datasource.retrofit.BackendApi
import cz.vvoleman.phr.feature_medicine.data.datasource.retrofit.medicine.mapper.MedicineApiDataSourceModelToDataMapper
import cz.vvoleman.phr.feature_medicine.data.datasource.retrofit.medicine.mapper.PackagingApiDataSourceModelToDataMapper
import cz.vvoleman.phr.feature_medicine.data.datasource.retrofit.medicine.mapper.ProductFormApiDataSourceModelToDataMapper
import cz.vvoleman.phr.feature_medicine.data.datasource.retrofit.medicine.mapper.SubstanceApiDataSourceModelToDataMapper
import cz.vvoleman.phr.feature_medicine.data.datasource.room.medicine.SubstanceAmountDataSourceModel
import cz.vvoleman.phr.feature_medicine.data.datasource.room.medicine.dao.ProductFormDao
import cz.vvoleman.phr.feature_medicine.data.datasource.room.medicine.dao.SubstanceDao
import cz.vvoleman.phr.feature_medicine.data.datasource.room.medicine.mapper.*
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
    fun providesProductFormDao(db: PatientDatabase) = db.productFormDao()

    @Provides
    fun providesSubstanceDao(db: PatientDatabase) = db.substanceDao()

    @Provides
    @Singleton
    fun provideBackendApi(retrofit: Retrofit): BackendApi =
        retrofit
            .create(BackendApi::class.java)

    @Provides
    fun providesSubstanceAmountDataSourceModelToDataMapper(
        substanceDao: SubstanceDao,
        substanceDataSourceModelToDataMapper: SubstanceDataSourceModelToDataMapper
    ) = SubstanceAmountDataSourceModelToDataMapper(
        substanceDao,
        substanceDataSourceModelToDataMapper
    )

    @Provides
    fun providesSubstanceDataSourceModelToDataMapper() = SubstanceDataSourceModelToDataMapper()

    @Provides
    fun providesProductFormDataSourceModelToDataMapper() = ProductFormDataSourceModelToDataMapper()

    @Provides
    fun providesPackagingDataSourceModelToDataMapper(
        productFormDao: ProductFormDao,
        productFormDataSourceModelToDataMapper: ProductFormDataSourceModelToDataMapper
    ) = PackagingDataSourceModelToDataMapper(
        productFormDao,
        productFormDataSourceModelToDataMapper
    )

    @Provides
    fun providesMedicineDataSourceModelToDataMapper(
        packagingDataSourceModelToDataMapper: PackagingDataSourceModelToDataMapper,
        substanceAmountDataSourceModelToDataMapper: SubstanceAmountDataSourceModelToDataMapper
    ) = MedicineDataSourceModelToDataMapper(
        packagingDataSourceModelToDataMapper,
        substanceAmountDataSourceModelToDataMapper
    )


    @Provides
    fun providesSubstanceApiDataSourceModelToDataMapper() = SubstanceApiDataSourceModelToDataMapper()


    @Provides
    fun providesProductFormApiDataSourceModelToDataMapper() = ProductFormApiDataSourceModelToDataMapper()

    @Provides
    fun providesPackagingApiDataSourceModelToDataMapper(
        productFormApiDataSourceModelToDataMapper: ProductFormApiDataSourceModelToDataMapper
    ) = PackagingApiDataSourceModelToDataMapper(
        productFormApiDataSourceModelToDataMapper
    )

    @Provides
    fun providesMedicineApiDataSourceModelToDataMapper(
        packagingApiDataSourceModelToDataMapper: PackagingApiDataSourceModelToDataMapper,
        substanceApiDataSourceModelToDataMapper: SubstanceApiDataSourceModelToDataMapper
    ) = MedicineApiDataSourceModelToDataMapper(
        packagingApiDataSourceModelToDataMapper,
        substanceApiDataSourceModelToDataMapper
    )

}