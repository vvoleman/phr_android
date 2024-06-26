package cz.vvoleman.phr.di.medicine

import android.content.Context
import cz.vvoleman.phr.base.presentation.navigation.NavManager
import cz.vvoleman.phr.base.ui.mapper.ViewStateBinder
import cz.vvoleman.phr.common.ui.component.nextSchedule.NextScheduleUiModelToPresentationMapper
import cz.vvoleman.phr.common.ui.mapper.frequencySelector.FrequencyDayUiModelToPresentationMapper
import cz.vvoleman.phr.common.ui.mapper.patient.PatientUiModelToPresentationMapper
import cz.vvoleman.phr.common.ui.mapper.problemCategory.ProblemCategoryUiModelToColorMapper
import cz.vvoleman.phr.common.ui.mapper.problemCategory.ProblemCategoryUiModelToPresentationMapper
import cz.vvoleman.phr.featureMedicine.databinding.FragmentAddEditMedicineBinding
import cz.vvoleman.phr.featureMedicine.databinding.FragmentListMedicineBinding
import cz.vvoleman.phr.featureMedicine.presentation.addEdit.model.AddEditMedicineViewState
import cz.vvoleman.phr.featureMedicine.presentation.list.model.ListMedicineViewState
import cz.vvoleman.phr.featureMedicine.presentation.provider.ProblemCategoryDetailProvider
import cz.vvoleman.phr.featureMedicine.ui.addEdit.mapper.AddEditMedicineDestinationUiMapper
import cz.vvoleman.phr.featureMedicine.ui.addEdit.mapper.TimeUiModelToPresentationMapper
import cz.vvoleman.phr.featureMedicine.ui.addEdit.view.AddEditMedicineBinder
import cz.vvoleman.phr.featureMedicine.ui.list.mapper.ListMedicineDestinationUiMapper
import cz.vvoleman.phr.featureMedicine.ui.list.mapper.MedicineScheduleUiModelToPresentationMapper
import cz.vvoleman.phr.featureMedicine.ui.list.mapper.MedicineUiModelToPresentationMapper
import cz.vvoleman.phr.featureMedicine.ui.list.mapper.PackagingUiModelToPresentationMapper
import cz.vvoleman.phr.featureMedicine.ui.list.mapper.ProductFormUiModelToPresentationMapper
import cz.vvoleman.phr.featureMedicine.ui.list.mapper.ScheduleItemUiModelToPresentationMapper
import cz.vvoleman.phr.featureMedicine.ui.list.mapper.ScheduleItemWithDetailsUiModelToPresentationMapper
import cz.vvoleman.phr.featureMedicine.ui.list.mapper.SubstanceAmountUiModelToPresentationMapper
import cz.vvoleman.phr.featureMedicine.ui.list.mapper.SubstanceUiModelToPresentationMapper
import cz.vvoleman.phr.featureMedicine.ui.list.view.ListMedicineBinder
import cz.vvoleman.phr.featureMedicine.ui.provider.problemCategoryDetail.MedicineScheduleProblemCategoryDetailProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class UiModule {

    @Provides
    fun providesListMedicineBinder(
        nextScheduleMapper: NextScheduleUiModelToPresentationMapper,
        medicineScheduleMapper: MedicineScheduleUiModelToPresentationMapper,
        scheduleItemMapper: ScheduleItemWithDetailsUiModelToPresentationMapper,
    ): ViewStateBinder<ListMedicineViewState, FragmentListMedicineBinding> =
        ListMedicineBinder(nextScheduleMapper, medicineScheduleMapper, scheduleItemMapper)

    @Provides
    fun providesListMedicineDestinationUiMapper(navManager: NavManager) =
        ListMedicineDestinationUiMapper(navManager)

    @Provides
    fun providesAddEditMedicineDestinationUiMapper(navManager: NavManager) =
        AddEditMedicineDestinationUiMapper(navManager)

    @Provides
    fun providesAddEditMedicineBinder(
        medicineMapper: MedicineUiModelToPresentationMapper,
        timeMapper: TimeUiModelToPresentationMapper,
        frequencyMapper: FrequencyDayUiModelToPresentationMapper,
        problemCategoryMapper: ProblemCategoryUiModelToColorMapper
    ): ViewStateBinder<AddEditMedicineViewState, FragmentAddEditMedicineBinding> =
        AddEditMedicineBinder(medicineMapper, timeMapper, frequencyMapper, problemCategoryMapper)

    @Provides
    fun provideProductFormUiModelToPresentationMapper(): ProductFormUiModelToPresentationMapper {
        return ProductFormUiModelToPresentationMapper()
    }

    @Provides
    fun providesPackagingUiModelToPresentationMapper(
        productFormMapper: ProductFormUiModelToPresentationMapper
    ): PackagingUiModelToPresentationMapper {
        return PackagingUiModelToPresentationMapper(
            productFormMapper
        )
    }

    @Provides
    fun providesSubstanceUiModelToPresentationMapper(): SubstanceUiModelToPresentationMapper {
        return SubstanceUiModelToPresentationMapper()
    }

    @Provides
    fun providesSubstanceAmountUiModelToPresentationMapper(
        substanceMapper: SubstanceUiModelToPresentationMapper
    ): SubstanceAmountUiModelToPresentationMapper {
        return SubstanceAmountUiModelToPresentationMapper(substanceMapper)
    }

    @Provides
    fun providesMedicineUiModelToPresentationMapper(
        packagingMapper: PackagingUiModelToPresentationMapper,
        substanceAmountMapper: SubstanceAmountUiModelToPresentationMapper
    ): MedicineUiModelToPresentationMapper {
        return MedicineUiModelToPresentationMapper(
            packagingMapper,
            substanceAmountMapper
        )
    }

    @Provides
    fun providesTimeUiModelToPresentationMapper(): TimeUiModelToPresentationMapper {
        return TimeUiModelToPresentationMapper()
    }

    @Provides
    fun providesScheduleItemUiModelToPresentationMapper(): ScheduleItemUiModelToPresentationMapper {
        return ScheduleItemUiModelToPresentationMapper()
    }

    @Provides
    fun providesMedicineScheduleUiModelToPresentationMapper(
        patientMapper: PatientUiModelToPresentationMapper,
        scheduleMapper: ScheduleItemUiModelToPresentationMapper,
        medicineMapper: MedicineUiModelToPresentationMapper,
        problemCategoryMapper: ProblemCategoryUiModelToPresentationMapper
    ): MedicineScheduleUiModelToPresentationMapper {
        return MedicineScheduleUiModelToPresentationMapper(
            patientMapper,
            scheduleMapper,
            medicineMapper,
            problemCategoryMapper
        )
    }

    @Provides
    fun providesScheduleItemWithDetailsUiModelToPresentationMapper(
        scheduleItemMapper: ScheduleItemUiModelToPresentationMapper,
        patientMapper: PatientUiModelToPresentationMapper,
        medicineMapper: MedicineUiModelToPresentationMapper
    ): ScheduleItemWithDetailsUiModelToPresentationMapper {
        return ScheduleItemWithDetailsUiModelToPresentationMapper(
            scheduleItemMapper,
            patientMapper,
            medicineMapper
        )
    }

    @Provides
    fun providesMedicineScheduleProblemCategoryDetailProvider(
        scheduleMapper: MedicineScheduleUiModelToPresentationMapper,
        @ApplicationContext context: Context
    ): ProblemCategoryDetailProvider {
        return MedicineScheduleProblemCategoryDetailProvider(
            scheduleMapper,
            context
        )
    }
}
