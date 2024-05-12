package cz.vvoleman.phr.di.medicine

import cz.vvoleman.phr.common.data.mapper.PatientDataSourceModelToDomainMapper
import cz.vvoleman.phr.common.data.mapper.problemCategory.ProblemCategoryDataSourceModelToDomainMapper
import cz.vvoleman.phr.data.PatientDatabase
import cz.vvoleman.phr.featureMedicine.data.datasource.retrofit.BackendApi
import cz.vvoleman.phr.featureMedicine.data.datasource.retrofit.medicine.mapper.MedicineApiDataSourceModelToDataMapper
import cz.vvoleman.phr.featureMedicine.data.datasource.retrofit.medicine.mapper.PackagingApiDataSourceModelToDataMapper
import cz.vvoleman.phr.featureMedicine.data.datasource.retrofit.medicine.mapper.ProductFormApiDataSourceModelToDataMapper
import cz.vvoleman.phr.featureMedicine.data.datasource.retrofit.medicine.mapper.SubstanceApiDataSourceModelToDataMapper
import cz.vvoleman.phr.featureMedicine.data.datasource.room.medicine.dao.ProductFormDao
import cz.vvoleman.phr.featureMedicine.data.datasource.room.medicine.dao.SubstanceDao
import cz.vvoleman.phr.featureMedicine.data.datasource.room.medicine.mapper.*
import cz.vvoleman.phr.featureMedicine.data.datasource.room.schedule.mapper.MedicineScheduleDataSourceModelToDataMapper
import cz.vvoleman.phr.featureMedicine.data.datasource.room.schedule.mapper.SaveMedicineScheduleDataSourceModelToDataMapper
import cz.vvoleman.phr.featureMedicine.data.datasource.room.schedule.mapper.ScheduleItemDataSourceModelToDataMapper
import cz.vvoleman.phr.featureMedicine.data.mapper.*
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
    fun providesMedicineScheduleDao(db: PatientDatabase) = db.medicineScheduleDao()

    @Provides
    fun providesScheduleItemDao(db: PatientDatabase) = db.scheduleItemDao()

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

    @Provides
    fun providesScheduleItemDataSourceModelToDataMapper() = ScheduleItemDataSourceModelToDataMapper()

    @Provides
    fun providesMedicineDataToDataSourceModelMapper(
        patientDataSourceModelToDomainMapper: PatientDataSourceModelToDomainMapper,
        medicineDataSourceModelToDataMapper: MedicineDataSourceModelToDataMapper,
        scheduleItemDataSourceModelToDataMapper: ScheduleItemDataSourceModelToDataMapper,
        problemCategoryMapper: ProblemCategoryDataSourceModelToDomainMapper
    ) = MedicineScheduleDataSourceModelToDataMapper(
        patientDataSourceModelToDomainMapper,
        medicineDataSourceModelToDataMapper,
        scheduleItemDataSourceModelToDataMapper,
        problemCategoryMapper
    )

    @Provides
    fun providesSaveMedicineScheduleDataSourcesModelToDataMapper() =
        SaveMedicineScheduleDataSourceModelToDataMapper()
}
