package cz.vvoleman.phr.di.medicine

import android.content.Context
import cz.vvoleman.phr.common.presentation.eventBus.CommonEventBus
import cz.vvoleman.phr.common.presentation.mapper.PatientPresentationModelToDomainMapper
import cz.vvoleman.phr.common.presentation.mapper.problemCategory.ProblemCategoryPresentationModelToDomainMapper
import cz.vvoleman.phr.featureMedicine.domain.repository.GetSchedulesByProblemCategoryRepository
import cz.vvoleman.phr.featureMedicine.presentation.addEdit.mapper.SaveMedicineSchedulePresentationModelToDomainMapper
import cz.vvoleman.phr.featureMedicine.presentation.addEdit.mapper.SaveScheduleItemPresentationModelToDomainMapper
import cz.vvoleman.phr.featureMedicine.presentation.list.mapper.MedicinePresentationModelToDomainMapper
import cz.vvoleman.phr.featureMedicine.presentation.list.mapper.MedicineSchedulePresentationModelToDomainMapper
import cz.vvoleman.phr.featureMedicine.presentation.list.mapper.PackagingPresentationModelToDomainMapper
import cz.vvoleman.phr.featureMedicine.presentation.list.mapper.ProductFormPresentationModelToDomainMapper
import cz.vvoleman.phr.featureMedicine.presentation.list.mapper.ScheduleItemPresentationModelToDomainMapper
import cz.vvoleman.phr.featureMedicine.presentation.list.mapper.ScheduleItemWithDetailsDomainModelToNextScheduleMapper
import cz.vvoleman.phr.featureMedicine.presentation.list.mapper.ScheduleItemWithDetailsPresentationModelToDomainMapper
import cz.vvoleman.phr.featureMedicine.presentation.list.mapper.SubstanceAmountPresentationModelToDomainMapper
import cz.vvoleman.phr.featureMedicine.presentation.list.mapper.SubstancePresentationModelToDomainMapper
import cz.vvoleman.phr.featureMedicine.presentation.provider.ProblemCategoryDetailProvider
import cz.vvoleman.phr.featureMedicine.presentation.subscriber.MedicineListener
import cz.vvoleman.phr.featureMedicine.ui.list.mapper.MedicineScheduleUiModelToPresentationMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
        scheduleMapper: ScheduleItemPresentationModelToDomainMapper,
        problemCategoryMapper: ProblemCategoryPresentationModelToDomainMapper,
    ): SaveMedicineSchedulePresentationModelToDomainMapper {
        return SaveMedicineSchedulePresentationModelToDomainMapper(
            patientMapper,
            medicineMapper,
            scheduleMapper,
            problemCategoryMapper
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
        medicineMapper: MedicinePresentationModelToDomainMapper,
        problemCategoryMapper: ProblemCategoryPresentationModelToDomainMapper
    ): MedicineSchedulePresentationModelToDomainMapper {
        return MedicineSchedulePresentationModelToDomainMapper(
            patientMapper,
            scheduleMapper,
            medicineMapper,
            problemCategoryMapper
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
    fun providesScheduleItemWithDetailsDomainModelToNextScheduleMapper() =
        ScheduleItemWithDetailsDomainModelToNextScheduleMapper()

    @Provides
    fun providesMedicineListener(
        commonEventBus: CommonEventBus,
        getSchedulesByProblemCategoryRepository: GetSchedulesByProblemCategoryRepository,
        problemCategoryDetailProvider: ProblemCategoryDetailProvider,
        scheduleMapper: MedicineSchedulePresentationModelToDomainMapper,
        scheduleUiMapper: MedicineScheduleUiModelToPresentationMapper,
        @ApplicationContext context: Context
    ): MedicineListener {
        return MedicineListener(
            commonEventBus = commonEventBus,
            getSchedulesByProblemCategoryRepository = getSchedulesByProblemCategoryRepository,
            problemCategoryDetailProvider = problemCategoryDetailProvider,
            scheduleMapper = scheduleMapper,
            scheduleUiMapper = scheduleUiMapper,
            context = context,
        )
    }
}
