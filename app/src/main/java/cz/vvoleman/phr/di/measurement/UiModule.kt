package cz.vvoleman.phr.di.measurement

import android.content.Context
import cz.vvoleman.phr.base.presentation.navigation.NavManager
import cz.vvoleman.phr.base.ui.mapper.ViewStateBinder
import cz.vvoleman.phr.common.ui.component.nextSchedule.NextScheduleUiModelToPresentationMapper
import cz.vvoleman.phr.common.ui.mapper.frequencySelector.FrequencyDayUiModelToPresentationMapper
import cz.vvoleman.phr.common.ui.mapper.patient.PatientUiModelToPresentationMapper
import cz.vvoleman.phr.common.ui.mapper.problemCategory.ProblemCategoryUiModelToColorMapper
import cz.vvoleman.phr.common.ui.mapper.problemCategory.ProblemCategoryUiModelToPresentationMapper
import cz.vvoleman.phr.featureMeasurement.databinding.FragmentAddEditEntryBinding
import cz.vvoleman.phr.featureMeasurement.databinding.FragmentAddEditMeasurementBinding
import cz.vvoleman.phr.featureMeasurement.databinding.FragmentDetailMeasurementGroupBinding
import cz.vvoleman.phr.featureMeasurement.databinding.FragmentListMeasurementBinding
import cz.vvoleman.phr.featureMeasurement.presentation.model.addEdit.AddEditMeasurementViewState
import cz.vvoleman.phr.featureMeasurement.presentation.model.addEditEntry.AddEditEntryViewState
import cz.vvoleman.phr.featureMeasurement.presentation.model.detail.DetailMeasurementGroupViewState
import cz.vvoleman.phr.featureMeasurement.presentation.model.list.ListMeasurementViewState
import cz.vvoleman.phr.featureMeasurement.presentation.subscriber.ProblemCategoryDetailProvider
import cz.vvoleman.phr.featureMeasurement.ui.component.reminderTimeSelector.TimeUiModelToPresentationMapper
import cz.vvoleman.phr.featureMeasurement.ui.mapper.addEdit.destination.AddEditMeasurementDestinationUiMapper
import cz.vvoleman.phr.featureMeasurement.ui.mapper.addEditEntry.EntryFieldUiModelToEntryFieldItemMapper
import cz.vvoleman.phr.featureMeasurement.ui.mapper.addEditEntry.EntryFieldUiModelToPresentationMapper
import cz.vvoleman.phr.featureMeasurement.ui.mapper.addEditEntry.destination.AddEditEntryDestinationUiMapper
import cz.vvoleman.phr.featureMeasurement.ui.mapper.core.MeasurementGroupEntryUiModelToPresentationMapper
import cz.vvoleman.phr.featureMeasurement.ui.mapper.core.MeasurementGroupFieldUiModelToPresentationMapper
import cz.vvoleman.phr.featureMeasurement.ui.mapper.core.MeasurementGroupScheduleItemUiModelToPresentationMapper
import cz.vvoleman.phr.featureMeasurement.ui.mapper.core.MeasurementGroupUiModelToPresentationMapper
import cz.vvoleman.phr.featureMeasurement.ui.mapper.core.NumericFieldUiModelToPresentationMapper
import cz.vvoleman.phr.featureMeasurement.ui.mapper.core.ScheduledMeasurementGroupUiModelToPresentationMapper
import cz.vvoleman.phr.featureMeasurement.ui.mapper.core.UnitGroupUiModelToPresentationMapper
import cz.vvoleman.phr.featureMeasurement.ui.mapper.core.UnitUiModelToPresentationMapper
import cz.vvoleman.phr.featureMeasurement.ui.mapper.detail.EntryInfoUiModelToMeasurementGroupMapper
import cz.vvoleman.phr.featureMeasurement.ui.mapper.detail.FieldStatsUiModelToPresentationMapper
import cz.vvoleman.phr.featureMeasurement.ui.mapper.detail.MeasurementGroupWithStatsUiModelToPresentationMapper
import cz.vvoleman.phr.featureMeasurement.ui.mapper.detail.destination.DetailMeasurementGroupDestinationUiMapper
import cz.vvoleman.phr.featureMeasurement.ui.mapper.list.destination.ListMeasurementDestinationUiMapper
import cz.vvoleman.phr.featureMeasurement.ui.subscriber.MeasurementProblemCategoryDetailProvider
import cz.vvoleman.phr.featureMeasurement.ui.view.addEdit.AddEditMeasurementBinder
import cz.vvoleman.phr.featureMeasurement.ui.view.addEditEntry.AddEditEntryBinder
import cz.vvoleman.phr.featureMeasurement.ui.view.detail.DetailMeasurementGroupBinder
import cz.vvoleman.phr.featureMeasurement.ui.view.list.ListMeasurementBinder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class UiModule {

    @Provides
    fun providesListMeasurementDestinationUiMapper(
        @ApplicationContext context: Context,
        navManager: NavManager
    ) = ListMeasurementDestinationUiMapper(context, navManager)

    @Provides
    fun providesListMeasurementViewBinder(
        nextScheduleMapper: NextScheduleUiModelToPresentationMapper,
        measurementGroupMapper: MeasurementGroupUiModelToPresentationMapper,
        scheduledMapper: ScheduledMeasurementGroupUiModelToPresentationMapper,
    ): ViewStateBinder<ListMeasurementViewState, FragmentListMeasurementBinding> =
        ListMeasurementBinder(
            nextScheduleMapper = nextScheduleMapper,
            measurementGroupMapper = measurementGroupMapper,
            scheduledMapper = scheduledMapper,
        )

    @Provides
    fun providesAddEditMeasurementDestinationUiMapper(
        navManager: NavManager
    ) = AddEditMeasurementDestinationUiMapper(navManager)

    @Provides
    fun providesAddEditMeasurementViewBinder(
        fieldMapper: MeasurementGroupFieldUiModelToPresentationMapper,
        unitGroupMapper: UnitGroupUiModelToPresentationMapper,
        frequencyMapper: FrequencyDayUiModelToPresentationMapper,
        problemCategoryColorMapper: ProblemCategoryUiModelToColorMapper,
    ): ViewStateBinder<AddEditMeasurementViewState, FragmentAddEditMeasurementBinding> =
        AddEditMeasurementBinder(
            fieldMapper = fieldMapper,
            unitGroupMapper = unitGroupMapper,
            frequencyMapper = frequencyMapper,
            problemCategoryMapper = problemCategoryColorMapper,
        )

    @Provides
    fun providesTimeUiModelToPresentationMapper() = TimeUiModelToPresentationMapper()

    @Provides
    fun provideNumericFieldUiModelToPresentationMapper(
        unitGroupMapper: UnitGroupUiModelToPresentationMapper,
    ): NumericFieldUiModelToPresentationMapper {
        return NumericFieldUiModelToPresentationMapper(
            unitGroupMapper = unitGroupMapper,
        )
    }

    @Provides
    fun provideUnitGroupUiModelToPresentationMapper(
        unitMapper: UnitUiModelToPresentationMapper,
    ): UnitGroupUiModelToPresentationMapper {
        return UnitGroupUiModelToPresentationMapper(
            unitMapper = unitMapper,
        )
    }

    @Provides
    fun provideUnitUiModelToPresentationMapper(): UnitUiModelToPresentationMapper {
        return UnitUiModelToPresentationMapper()
    }

    @Provides
    fun provideMeasurementGroupScheduleItemUiModelToPresentationMapper(): MeasurementGroupScheduleItemUiModelToPresentationMapper {
        return MeasurementGroupScheduleItemUiModelToPresentationMapper()
    }

    @Provides
    fun provideMeasurementGroupEntryUiModelToPresentationMapper(): MeasurementGroupEntryUiModelToPresentationMapper {
        return MeasurementGroupEntryUiModelToPresentationMapper()
    }

    @Provides
    fun provideMeasurementGroupUiModelToPresentationMapper(
        scheduleItemMapper: MeasurementGroupScheduleItemUiModelToPresentationMapper,
        entryMapper: MeasurementGroupEntryUiModelToPresentationMapper,
        fieldMapper: MeasurementGroupFieldUiModelToPresentationMapper,
        patientMapper: PatientUiModelToPresentationMapper,
        problemCategoryMapper: ProblemCategoryUiModelToPresentationMapper,
    ): MeasurementGroupUiModelToPresentationMapper {
        return MeasurementGroupUiModelToPresentationMapper(
            scheduleItemMapper = scheduleItemMapper,
            entryMapper = entryMapper,
            fieldMapper = fieldMapper,
            patientMapper = patientMapper,
            problemCategoryMapper = problemCategoryMapper,
        )
    }

    @Provides
    fun providesMeasurementGroupFieldUiToPresentationMapper(
        numericFieldMapper: NumericFieldUiModelToPresentationMapper
    ) = MeasurementGroupFieldUiModelToPresentationMapper(numericFieldMapper)

    @Provides
    fun providesScheduledMeasurementGroupUiModelToPresentationMapper(
        measurementGroupMapper: MeasurementGroupUiModelToPresentationMapper
    ) = ScheduledMeasurementGroupUiModelToPresentationMapper(measurementGroupMapper)

    @Provides
    fun providesAddEditEntryDestinationUiMapper(
        navManager: NavManager
    ) = AddEditEntryDestinationUiMapper(navManager)

    @Provides
    fun providesAddEditEntryBinder(
        fieldMapper: MeasurementGroupFieldUiModelToPresentationMapper,
        entryFieldMapper: EntryFieldUiModelToPresentationMapper,
        entryFieldItemMapper: EntryFieldUiModelToEntryFieldItemMapper,
    ): ViewStateBinder<AddEditEntryViewState, FragmentAddEditEntryBinding> =
        AddEditEntryBinder(
            fieldMapper = fieldMapper,
            entryFieldMapper = entryFieldMapper,
            entryFieldItemMapper = entryFieldItemMapper
        )

    @Provides
    fun providesEntryFieldUiModelToPresentationMapper() = EntryFieldUiModelToPresentationMapper()

    @Provides
    fun providesEntryFieldUiModelToEntryFieldItemMapper() = EntryFieldUiModelToEntryFieldItemMapper()

    @Provides
    fun providesDetailMeasurementGroupDestinationUiMapper(
        @ApplicationContext context: Context,
        navManager: NavManager
    ) = DetailMeasurementGroupDestinationUiMapper(context, navManager)

    @Provides
    fun providesDetailMeasurementGroupBinder(
        entryInfoMapper: EntryInfoUiModelToMeasurementGroupMapper,
        fieldStatsMapper: FieldStatsUiModelToPresentationMapper,
    ): ViewStateBinder<DetailMeasurementGroupViewState, FragmentDetailMeasurementGroupBinding> =
        DetailMeasurementGroupBinder(
            entryInfoMapper = entryInfoMapper,
            statsMapper = fieldStatsMapper
        )

    @Provides
    fun providesFieldStatsUiModelToPresentationMapper() = FieldStatsUiModelToPresentationMapper()

    @Provides
    fun providesEntryInfoUiModelToMeasurementGroupMapper(
        measurementGroupMapper: MeasurementGroupUiModelToPresentationMapper
    ) = EntryInfoUiModelToMeasurementGroupMapper(measurementGroupMapper)

    @Provides
    fun providesProblemCategoryDetailProvider(
        measurementGroupMapper: MeasurementGroupWithStatsUiModelToPresentationMapper,
        @ApplicationContext context: Context
    ): ProblemCategoryDetailProvider {
        return MeasurementProblemCategoryDetailProvider(
            measurementGroupMapper,
            context
        )
    }

    @Provides
    fun providesMeasurementGroupWithStatsUiModelToPresentationMapper(
        fieldStatsMapper: FieldStatsUiModelToPresentationMapper,
        measurementGroupMapper: MeasurementGroupUiModelToPresentationMapper
    ): MeasurementGroupWithStatsUiModelToPresentationMapper {
        return MeasurementGroupWithStatsUiModelToPresentationMapper(
            fieldStatsMapper = fieldStatsMapper,
            measurementGroupMapper = measurementGroupMapper
        )
    }
}
