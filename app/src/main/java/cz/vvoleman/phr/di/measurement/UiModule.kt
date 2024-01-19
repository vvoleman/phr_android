package cz.vvoleman.phr.di.measurement

import android.content.Context
import cz.vvoleman.featureMeasurement.databinding.FragmentAddEditMeasurementBinding
import cz.vvoleman.featureMeasurement.databinding.FragmentListMeasurementBinding
import cz.vvoleman.featureMeasurement.presentation.model.addEdit.AddEditMeasurementViewState
import cz.vvoleman.featureMeasurement.presentation.model.list.ListMeasurementViewState
import cz.vvoleman.featureMeasurement.ui.component.timeSelector.TimeUiModelToPresentationMapper
import cz.vvoleman.featureMeasurement.ui.mapper.addEdit.destination.AddEditMeasurementDestinationUiMapper
import cz.vvoleman.featureMeasurement.ui.mapper.core.MeasurementGroupEntryUiModelToPresentationMapper
import cz.vvoleman.featureMeasurement.ui.mapper.core.MeasurementGroupScheduleItemUiModelToPresentationMapper
import cz.vvoleman.featureMeasurement.ui.mapper.core.MeasurementGroupUiModelToPresentationMapper
import cz.vvoleman.featureMeasurement.ui.mapper.core.NumericFieldUiModelToPresentationMapper
import cz.vvoleman.featureMeasurement.ui.mapper.core.UnitGroupUiModelToPresentationMapper
import cz.vvoleman.featureMeasurement.ui.mapper.core.UnitUiModelToPresentationMapper
import cz.vvoleman.featureMeasurement.ui.mapper.list.destination.ListMeasurementDestinationUiMapper
import cz.vvoleman.featureMeasurement.ui.view.addEdit.AddEditMeasurementBinder
import cz.vvoleman.featureMeasurement.ui.view.list.ListMeasurementBinder
import cz.vvoleman.phr.base.presentation.navigation.NavManager
import cz.vvoleman.phr.base.ui.mapper.ViewStateBinder
import cz.vvoleman.phr.common.ui.mapper.patient.PatientUiModelToPresentationMapper
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
    ): ViewStateBinder<ListMeasurementViewState, FragmentListMeasurementBinding> = ListMeasurementBinder()

    @Provides
    fun providesAddEditMeasurementDestinationUiMapper(
        navManager: NavManager
    ) = AddEditMeasurementDestinationUiMapper(navManager)

    @Provides
    fun providesAddEditMeasurementViewBinder(
    ): ViewStateBinder<AddEditMeasurementViewState, FragmentAddEditMeasurementBinding> = AddEditMeasurementBinder()

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
        numericFieldMapper: NumericFieldUiModelToPresentationMapper,
        patientMapper: PatientUiModelToPresentationMapper,
    ): MeasurementGroupUiModelToPresentationMapper {
        return MeasurementGroupUiModelToPresentationMapper(
            scheduleItemMapper = scheduleItemMapper,
            entryMapper = entryMapper,
            numericFieldMapper = numericFieldMapper,
            patientMapper = patientMapper
        )
    }

}
