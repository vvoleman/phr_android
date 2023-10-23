package cz.vvoleman.phr.di.medicine

import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.common.presentation.mapper.PatientPresentationModelToDomainMapper
import cz.vvoleman.phr.featureMedicine.domain.repository.SearchMedicineRepository
import cz.vvoleman.phr.featureMedicine.domain.repository.timeline.GetSchedulesByPatientRepository
import cz.vvoleman.phr.featureMedicine.domain.usecase.GetScheduledInTimeRangeUseCase
import cz.vvoleman.phr.featureMedicine.domain.usecase.SearchMedicineUseCase
import cz.vvoleman.phr.featureMedicine.presentation.mapper.addEdit.SaveMedicineSchedulePresentationModelToDomainMapper
import cz.vvoleman.phr.featureMedicine.presentation.mapper.addEdit.SaveScheduleItemPresentationModelToDomainMapper
import cz.vvoleman.phr.featureMedicine.presentation.mapper.list.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class PresentationModule {

    @Provides
    fun providesSearchMedicineUseCase(
        searchMedicineRepository: SearchMedicineRepository,
        coroutineContextProvider: CoroutineContextProvider
    ) = SearchMedicineUseCase(
        searchMedicineRepository,
        coroutineContextProvider
    )

    @Provides
    fun providesGetScheduledInTimeRangeUseCase(
        getSchedulesByPatientRepository: GetSchedulesByPatientRepository,
        coroutineContextProvider: CoroutineContextProvider
    ) = GetScheduledInTimeRangeUseCase(
        getSchedulesByPatientRepository,
        coroutineContextProvider
    )

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
}
