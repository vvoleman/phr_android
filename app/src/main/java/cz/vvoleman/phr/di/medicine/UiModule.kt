package cz.vvoleman.phr.di.medicine

import cz.vvoleman.phr.base.presentation.navigation.NavManager
import cz.vvoleman.phr.base.ui.mapper.ViewStateBinder
import cz.vvoleman.phr.common.ui.mapper.patient.PatientUiModelToPresentationMapper
import cz.vvoleman.phr.featureMedicine.databinding.FragmentAddEditMedicineBinding
import cz.vvoleman.phr.featureMedicine.databinding.FragmentExportBinding
import cz.vvoleman.phr.featureMedicine.databinding.FragmentListMedicineBinding
import cz.vvoleman.phr.featureMedicine.presentation.addEdit.model.AddEditMedicineViewState
import cz.vvoleman.phr.featureMedicine.presentation.export.model.ExportViewState
import cz.vvoleman.phr.featureMedicine.presentation.list.model.ListMedicineViewState
import cz.vvoleman.phr.featureMedicine.ui.addEdit.mapper.AddEditMedicineDestinationUiMapper
import cz.vvoleman.phr.featureMedicine.ui.addEdit.mapper.FrequencyDayUiModelToPresentationMapper
import cz.vvoleman.phr.featureMedicine.ui.addEdit.mapper.TimeUiModelToPresentationMapper
import cz.vvoleman.phr.featureMedicine.ui.addEdit.view.AddEditMedicineBinder
import cz.vvoleman.phr.featureMedicine.ui.export.mapper.ExportDestinationMapper
import cz.vvoleman.phr.featureMedicine.ui.export.mapper.ExportMedicineScheduleUiModelToPresentationMapper
import cz.vvoleman.phr.featureMedicine.ui.export.mapper.ExportScheduleItemUiModelToPresentationMapper
import cz.vvoleman.phr.featureMedicine.ui.export.mapper.ExportUiModelToPresentationMapper
import cz.vvoleman.phr.featureMedicine.ui.export.view.ExportBinder
import cz.vvoleman.phr.featureMedicine.ui.list.mapper.ListMedicineDestinationUiMapper
import cz.vvoleman.phr.featureMedicine.ui.list.mapper.MedicineScheduleUiModelToPresentationMapper
import cz.vvoleman.phr.featureMedicine.ui.list.mapper.MedicineUiModelToPresentationMapper
import cz.vvoleman.phr.featureMedicine.ui.list.mapper.NextScheduleItemUiModelToPresentationMapper
import cz.vvoleman.phr.featureMedicine.ui.list.mapper.PackagingUiModelToPresentationMapper
import cz.vvoleman.phr.featureMedicine.ui.list.mapper.ProductFormUiModelToPresentationMapper
import cz.vvoleman.phr.featureMedicine.ui.list.mapper.ScheduleItemUiModelToPresentationMapper
import cz.vvoleman.phr.featureMedicine.ui.list.mapper.ScheduleItemWithDetailsUiModelToPresentationMapper
import cz.vvoleman.phr.featureMedicine.ui.list.mapper.SubstanceAmountUiModelToPresentationMapper
import cz.vvoleman.phr.featureMedicine.ui.list.mapper.SubstanceUiModelToPresentationMapper
import cz.vvoleman.phr.featureMedicine.ui.list.view.ListMedicineBinder
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
        medicineScheduleMapper: MedicineScheduleUiModelToPresentationMapper,
        scheduleItemMapper: ScheduleItemWithDetailsUiModelToPresentationMapper,
    ): ViewStateBinder<ListMedicineViewState, FragmentListMedicineBinding> =
        ListMedicineBinder(nextScheduleItemMapper, medicineScheduleMapper, scheduleItemMapper)

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

    @Provides
    fun providesExportDestinationMapper(navManager: NavManager) =
        ExportDestinationMapper(navManager)

    @Provides
    fun providesExportViewStateBinder(
        exportUiMapper: ExportUiModelToPresentationMapper
    ): ViewStateBinder<ExportViewState, FragmentExportBinding> =
        ExportBinder(exportUiMapper)

    @Provides
    fun providesExportScheduleItemUiModelToPresentationMapper() =
        ExportScheduleItemUiModelToPresentationMapper()

    @Provides
    fun providesExportMedicineScheduleUiModelToPresentationMapper(
        medicineMapper: MedicineUiModelToPresentationMapper,
        patientMapper: PatientUiModelToPresentationMapper,
        scheduleItemMapper: ExportScheduleItemUiModelToPresentationMapper
    ) = ExportMedicineScheduleUiModelToPresentationMapper(
        medicineMapper,
        patientMapper,
        scheduleItemMapper
    )

    @Provides
    fun providesExportUiModelToPresentationMapper() = ExportUiModelToPresentationMapper()
}
