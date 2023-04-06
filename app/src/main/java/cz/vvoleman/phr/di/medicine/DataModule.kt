package cz.vvoleman.phr.di.medicine

import cz.vvoleman.phr.data.PatientDatabase
import cz.vvoleman.phr.feature_medicine.data.datasource.retrofit.BackendApi
import cz.vvoleman.phr.feature_medicine.data.datasource.retrofit.medicine.mapper.MedicineApiDataSourceModelToDataMapper
import cz.vvoleman.phr.feature_medicine.data.datasource.room.medicine.dao.*
import cz.vvoleman.phr.feature_medicine.data.datasource.room.medicine.mapper.MedicineDataSourceModelToDataMapper
import cz.vvoleman.phr.feature_medicine.data.datasource.room.medicine.mapper.ProductFormDataSourceModelToDataMapper
import cz.vvoleman.phr.feature_medicine.data.datasource.room.medicine.mapper.SubstanceDataSourceModelToDataMapper
import cz.vvoleman.phr.feature_medicine.data.mapper.*
import cz.vvoleman.phr.feature_medicine.data.repository.MedicineRepository
import cz.vvoleman.phr.feature_medicine.domain.repository.SearchMedicineRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    fun providesMedicineRepository(
        backendApi: BackendApi,
        medicineDao: MedicineDao,
        substanceDao: SubstanceDao,
        substanceDataMapper: SubstanceDataModelToDomainMapper,
        substanceDataSourceMapper: SubstanceDataSourceModelToDataMapper,
        productFormDao: ProductFormDao,
        productFormDataMapper: ProductFormDataModelToDomainMapper,
        productFormDataSourceMapper: ProductFormDataSourceModelToDataMapper,
        medicineApiMapper: MedicineApiDataSourceModelToDataMapper,
        medicineDataMapper: MedicineDataModelToDomainMapper,
        medicineDataSourceMapper: MedicineDataSourceModelToDataMapper
    ) = MedicineRepository(
        backendApi,
        medicineDao,
        substanceDao,
        productFormDao,
        medicineApiMapper,
        medicineDataMapper,
        medicineDataSourceMapper,
        substanceDataMapper,
        substanceDataSourceMapper,
        productFormDataMapper,
        productFormDataSourceMapper
    )

    @Provides
    fun providesSearchMedicineRepository(
        medicineRepository: MedicineRepository,
    ): SearchMedicineRepository = medicineRepository

    @Provides
    fun providesSubstanceDataModelToDomainMapper() = SubstanceDataModelToDomainMapper()

    @Provides
    fun providesSubstanceAmountDataModelToDomainMapper(
        substanceDataModelToDomainMapper: SubstanceDataModelToDomainMapper
    ) = SubstanceAmountDataModelToDomainMapper(substanceDataModelToDomainMapper)

    @Provides
    fun providesProductFormDataModelToDomainMapper() = ProductFormDataModelToDomainMapper()

    @Provides
    fun providesPackagingDataModelToDomainMapper(
        productFormDataModelToDomainMapper: ProductFormDataModelToDomainMapper
    ) = PackagingDataModelToDomainMapper(productFormDataModelToDomainMapper)

    @Provides
    fun providesMedicineDataSourceModelToDomainMapper(
        packagingDataModelToDomainMapper: PackagingDataModelToDomainMapper,
        substanceAmountDataModelToDomainMapper: SubstanceAmountDataModelToDomainMapper
    ) = MedicineDataModelToDomainMapper(
        packagingDataModelToDomainMapper,
        substanceAmountDataModelToDomainMapper
    )

}