package cz.vvoleman.phr.di.medicine

import cz.vvoleman.phr.base.presentation.navigation.NavManager
import cz.vvoleman.phr.base.ui.mapper.ViewStateBinder
import cz.vvoleman.phr.common.ui.mapper.PatientUiModelToPresentationMapper
import cz.vvoleman.phr.featureMedicine.databinding.FragmentAddEditMedicineBinding
import cz.vvoleman.phr.featureMedicine.databinding.FragmentListMedicineBinding
import cz.vvoleman.phr.featureMedicine.presentation.addEdit.model.AddEditMedicineViewState
import cz.vvoleman.phr.featureMedicine.presentation.list.model.ListMedicineViewState
import cz.vvoleman.phr.featureMedicine.ui.addEdit.mapper.AddEditMedicineDestinationUiMapper
import cz.vvoleman.phr.featureMedicine.ui.addEdit.view.AddEditMedicineBinder
import cz.vvoleman.phr.featureMedicine.ui.list.mapper.ListMedicineDestinationUiMapper
import cz.vvoleman.phr.featureMedicine.ui.list.view.ListMedicineBinder
import cz.vvoleman.phr.featureMedicine.ui.mapper.addEdit.FrequencyDayUiModelToPresentationMapper
import cz.vvoleman.phr.featureMedicine.ui.mapper.addEdit.TimeUiModelToPresentationMapper
import cz.vvoleman.phr.featureMedicine.ui.mapper.list.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class UiModule {

    @Provides
    fun providesListMedicineBinder(
        nextScheduleItemMapper: NextScheduleItemUiModelToPresentationMapper,
        medicineMapper: MedicineUiModelToPresentationMapper,
        medicineScheduleMapper: MedicineScheduleUiModelToPresentationMapper,
        scheduleItemMapper: ScheduleItemWithDetailsUiModelToPresentationMapper,
    ): ViewStateBinder<ListMedicineViewState, FragmentListMedicineBinding> =
        ListMedicineBinder(nextScheduleItemMapper, medicineMapper, medicineScheduleMapper, scheduleItemMapper)

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
    ): ViewStateBinder<AddEditMedicineViewState, FragmentAddEditMedicineBinding> =
        AddEditMedicineBinder(medicineMapper, timeMapper, frequencyMapper)

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
    fun providesFrequencyDayUiModelToPresentationMapper(): FrequencyDayUiModelToPresentationMapper {
        return FrequencyDayUiModelToPresentationMapper()
    }

    @Provides
    fun providesScheduleItemUiModelToPresentationMapper(): ScheduleItemUiModelToPresentationMapper {
        return ScheduleItemUiModelToPresentationMapper()
    }

    @Provides
    fun providesMedicineScheduleUiModelToPresentationMapper(
        patientMapper: PatientUiModelToPresentationMapper,
        scheduleMapper: ScheduleItemUiModelToPresentationMapper,
        medicineMapper: MedicineUiModelToPresentationMapper
    ): MedicineScheduleUiModelToPresentationMapper {
        return MedicineScheduleUiModelToPresentationMapper(
            patientMapper,
            scheduleMapper,
            medicineMapper
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
    fun providesNextScheduleItemUiModelToPresentationMapper(
        mapper: ScheduleItemWithDetailsUiModelToPresentationMapper
    ) = NextScheduleItemUiModelToPresentationMapper(mapper)

}
