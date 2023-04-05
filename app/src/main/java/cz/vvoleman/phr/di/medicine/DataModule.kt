package cz.vvoleman.phr.di.medicine

import cz.vvoleman.phr.data.PatientDatabase
import cz.vvoleman.phr.feature_medicine.data.datasource.retrofit.BackendApi
import cz.vvoleman.phr.feature_medicine.data.datasource.room.medicine.dao.MedicineDao
import cz.vvoleman.phr.feature_medicine.data.mapper.MedicineApiModelToDbMapper
import cz.vvoleman.phr.feature_medicine.data.mapper.MedicineDataSourceModelToDomainMapper
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
        medicineApiModelToDbMapper: MedicineApiModelToDbMapper,
        medicineDataSourceModelToDomainMapper: MedicineDataSourceModelToDomainMapper,
        medicineDao: MedicineDao,
    ) = MedicineRepository(
        backendApi,
        medicineApiModelToDbMapper,
        medicineDataSourceModelToDomainMapper,
        medicineDao,
    )

    @Provides
    fun providesSearchMedicineRepository(
        medicineRepository: MedicineRepository,
    ): SearchMedicineRepository = medicineRepository

}