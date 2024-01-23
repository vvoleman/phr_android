package cz.vvoleman.phr.di.medicine

import cz.vvoleman.phr.common.presentation.mapper.PatientPresentationModelToDomainMapper
import cz.vvoleman.phr.featureMedicine.presentation.addEdit.mapper.SaveMedicineSchedulePresentationModelToDomainMapper
import cz.vvoleman.phr.featureMedicine.presentation.addEdit.mapper.SaveScheduleItemPresentationModelToDomainMapper
import cz.vvoleman.phr.featureMedicine.presentation.export.mapper.ExportMedicineSchedulePresentationModelToDomainMapper
import cz.vvoleman.phr.featureMedicine.presentation.export.mapper.ExportScheduleItemPresentationModelToDomainMapper
import cz.vvoleman.phr.featureMedicine.presentation.list.mapper.MedicinePresentationModelToDomainMapper
import cz.vvoleman.phr.featureMedicine.presentation.list.mapper.MedicineSchedulePresentationModelToDomainMapper
import cz.vvoleman.phr.featureMedicine.presentation.list.mapper.PackagingPresentationModelToDomainMapper
import cz.vvoleman.phr.featureMedicine.presentation.list.mapper.ProductFormPresentationModelToDomainMapper
import cz.vvoleman.phr.featureMedicine.presentation.list.mapper.ScheduleItemPresentationModelToDomainMapper
import cz.vvoleman.phr.featureMedicine.presentation.list.mapper.ScheduleItemWithDetailsDomainModelToNextScheduleMapper
import cz.vvoleman.phr.featureMedicine.presentation.list.mapper.ScheduleItemWithDetailsPresentationModelToDomainMapper
import cz.vvoleman.phr.featureMedicine.presentation.list.mapper.SubstanceAmountPresentationModelToDomainMapper
import cz.vvoleman.phr.featureMedicine.presentation.list.mapper.SubstancePresentationModelToDomainMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class PresentationModule {

    @Provides
    fun provideProductFormPresentationModelToDomainMapper(): ProductFormPresentationModelToDomainMapper {
        return ProductFormPresentationModelToDomainMapper()
    }

    @Provides
    fun providesPackagingPresentationModelToDomainMapper(
        productFormMapper: ProductFormPresentationModelToDomainMapper
    ): PackagingPresentationModelToDomainMapper {
        return PackagingPresentationModelToDomainMapper(
            productFormMapper
        )
    }

    @Provides
    fun providesSubstancePresentationModelToDomainMapper(): SubstancePresentationModelToDomainMapper {
        return SubstancePresentationModelToDomainMapper()
    }

    @Provides
    fun providesSubstanceAmountPresentationModelToDomainMapper(
        substanceMapper: SubstancePresentationModelToDomainMapper
    ): SubstanceAmountPresentationModelToDomainMapper {
        return SubstanceAmountPresentationModelToDomainMapper(substanceMapper)
    }

    @Provides
    fun providesMedicinePresentationModelToDomainMapper(
        packagingMapper: PackagingPresentationModelToDomainMapper,
        substanceAmountMapper: SubstanceAmountPresentationModelToDomainMapper
    ): MedicinePresentationModelToDomainMapper {
        return MedicinePresentationModelToDomainMapper(
            packagingMapper,
            substanceAmountMapper
        )
    }

    @Provides
    fun providesSaveMedicineSchedulePresentationModelToDomainMapper(
        patientMapper: PatientPresentationModelToDomainMapper,
        medicineMapper: MedicinePresentationModelToDomainMapper,
        scheduleMapper: ScheduleItemPresentationModelToDomainMapper
    ): SaveMedicineSchedulePresentationModelToDomainMapper {
        return SaveMedicineSchedulePresentationModelToDomainMapper(
            patientMapper,
            medicineMapper,
            scheduleMapper
        )
    }

    @Provides
    fun providesSaveScheduleItemPresentationModelToDomainMapper(): SaveScheduleItemPresentationModelToDomainMapper {
        return SaveScheduleItemPresentationModelToDomainMapper()
    }

    @Provides
    fun providesScheduleItemPresentationModelToDomainMapper() = ScheduleItemPresentationModelToDomainMapper()

    @Provides
    fun providesMedicineSchedulePresentationModelToDomainMapper(
        patientMapper: PatientPresentationModelToDomainMapper,
        scheduleMapper: ScheduleItemPresentationModelToDomainMapper,
        medicineMapper: MedicinePresentationModelToDomainMapper
    ): MedicineSchedulePresentationModelToDomainMapper {
        return MedicineSchedulePresentationModelToDomainMapper(
            patientMapper,
            scheduleMapper,
            medicineMapper
        )
    }

    @Provides
    fun providesScheduleItemWithDetailsPresentationModelToDomainMapper(
        scheduleItemMapper: ScheduleItemPresentationModelToDomainMapper,
        patientMapper: PatientPresentationModelToDomainMapper,
        medicineMapper: MedicinePresentationModelToDomainMapper
    ): ScheduleItemWithDetailsPresentationModelToDomainMapper {
        return ScheduleItemWithDetailsPresentationModelToDomainMapper(
            scheduleItemMapper,
            patientMapper,
            medicineMapper
        )
    }

    @Provides
    fun providesExportScheduleItemPresentationModelToDomainMapper(): ExportScheduleItemPresentationModelToDomainMapper {
        return ExportScheduleItemPresentationModelToDomainMapper()
    }

    @Provides
    fun providesExportMedicineSchedulePresentationModelToDomainMapper(
        medicineMapper: MedicinePresentationModelToDomainMapper,
        scheduleItemMapper: ExportScheduleItemPresentationModelToDomainMapper,
        patientMapper: PatientPresentationModelToDomainMapper
    ): ExportMedicineSchedulePresentationModelToDomainMapper {
        return ExportMedicineSchedulePresentationModelToDomainMapper(
            medicineMapper,
            scheduleItemMapper,
            patientMapper
        )
    }

    @Provides
    fun providesScheduleItemWithDetailsDomainModelToNextScheduleMapper() =
        ScheduleItemWithDetailsDomainModelToNextScheduleMapper()
}
