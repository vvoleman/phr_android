package cz.vvoleman.phr.di.medical_record

import cz.vvoleman.phr.feature_medicalrecord.data.datasource.model.room.category.ProblemCategoryDataSourceModel
import cz.vvoleman.phr.feature_medicalrecord.data.datasource.model.room.worker.MedicalWorkerDataSourceModel
import cz.vvoleman.phr.feature_medicalrecord.data.mapper.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    fun providesAddressDataSourceToDomainMapper() = AddressDataSourceToDomainMapper()

    @Provides
    fun providesDiagnoseDataSourceToDomainMapper() = DiagnoseDataSourceToDomainMapper()

    @Provides
    fun providesFilterRequestDomainModelToDataSourceMapper() =
        FilterRequestDomainModelToDataMapper()

    @Provides
    fun providesMedicalRecordDataSourceToDomainMapper(
        patient: PatientDataSourceToDomainMapper,
        diagnose: DiagnoseDataSourceToDomainMapper,
        medicalWorker: MedicalWorkerDataSourceToDomainMapper,
        problemCategoryDataSourceModel: ProblemCategoryDataSourceToDomainMapper,
    ) =
        MedicalRecordDataSourceToDomainMapper(
            patient,
            diagnose,
            medicalWorker,
            problemCategoryDataSourceModel
        )


}