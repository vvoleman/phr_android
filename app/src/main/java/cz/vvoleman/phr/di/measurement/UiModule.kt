package cz.vvoleman.phr.di.measurement

import android.content.Context
import cz.vvoleman.featureMeasurement.databinding.FragmentAddEditMeasurementBinding
import cz.vvoleman.featureMeasurement.databinding.FragmentListMeasurementBinding
import cz.vvoleman.phr.featureMeasurement.presentation.model.addEdit.AddEditMeasurementViewState
import cz.vvoleman.phr.featureMeasurement.presentation.model.list.ListMeasurementViewState
import cz.vvoleman.phr.featureMeasurement.ui.component.reminderTimeSelector.TimeUiModelToPresentationMapper
import cz.vvoleman.phr.featureMeasurement.ui.mapper.addEdit.destination.AddEditMeasurementDestinationUiMapper
import cz.vvoleman.phr.featureMeasurement.ui.mapper.core.MeasurementGroupEntryUiModelToPresentationMapper
import cz.vvoleman.phr.featureMeasurement.ui.mapper.core.MeasurementGroupFieldUiToPresentationMapper
import cz.vvoleman.phr.featureMeasurement.ui.mapper.core.MeasurementGroupScheduleItemUiModelToPresentationMapper
import cz.vvoleman.phr.featureMeasurement.ui.mapper.core.MeasurementGroupUiModelToPresentationMapper
import cz.vvoleman.phr.featureMeasurement.ui.mapper.core.NumericFieldUiModelToPresentationMapper
import cz.vvoleman.phr.featureMeasurement.ui.mapper.core.UnitGroupUiModelToPresentationMapper
import cz.vvoleman.phr.featureMeasurement.ui.mapper.core.UnitUiModelToPresentationMapper
import cz.vvoleman.phr.featureMeasurement.ui.mapper.list.destination.ListMeasurementDestinationUiMapper
import cz.vvoleman.phr.featureMeasurement.ui.view.addEdit.AddEditMeasurementBinder
import cz.vvoleman.phr.featureMeasurement.ui.view.list.ListMeasurementBinder
import cz.vvoleman.phr.base.presentation.navigation.NavManager
import cz.vvoleman.phr.base.ui.mapper.ViewStateBinder
import cz.vvoleman.phr.common.ui.mapper.frequencySelector.FrequencyDayUiModelToPresentationMapper
import cz.vvoleman.phr.common.ui.mapper.patient.PatientUiModelToPresentationMapper
import cz.vvoleman.phr.featureMeasurement.domain.facade.MeasurementTranslateDateTimeFacade
import cz.vvoleman.phr.featureMeasurement.presentation.mapper.core.MeasurementGroupPresentationModelToDomainMapper
import cz.vvoleman.phr.featureMeasurement.presentation.mapper.core.MeasurementGroupScheduleItemPresentationModelToDomainMapper
import cz.vvoleman.phr.featureMeasurement.ui.mapper.list.MeasurementGroupPresentationModelToNextScheduleMapper
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
    fun providesMeasurementGroupPresentationModelToNextScheduleMapper(
        translateFacade: MeasurementTranslateDateTimeFacade,
        domainMapper: MeasurementGroupScheduleItemPresentationModelToDomainMapper,
    ) =
        MeasurementGroupPresentationModelToNextScheduleMapper(
            translateFacade = translateFacade,
            domainMapper = domainMapper
        )

    @Provides
    fun providesListMeasurementViewBinder(
        nextScheduleMapper: MeasurementGroupPresentationModelToNextScheduleMapper
    ): ViewStateBinder<ListMeasurementViewState, FragmentListMeasurementBinding> =
        ListMeasurementBinder(nextScheduleMapper)

    @Provides
    fun providesAddEditMeasurementDestinationUiMapper(
        navManager: NavManager
    ) = AddEditMeasurementDestinationUiMapper(navManager)

    @Provides
    fun providesAddEditMeasurementViewBinder(
        fieldMapper: MeasurementGroupFieldUiToPresentationMapper,
        unitGroupMapper: UnitGroupUiModelToPresentationMapper,
        frequencyMapper: FrequencyDayUiModelToPresentationMapper,
    ): ViewStateBinder<AddEditMeasurementViewState, FragmentAddEditMeasurementBinding> =
        AddEditMeasurementBinder(
            fieldMapper = fieldMapper,
            unitGroupMapper = unitGroupMapper,
            frequencyMapper = frequencyMapper,
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
        fieldMapper: MeasurementGroupFieldUiToPresentationMapper,
        patientMapper: PatientUiModelToPresentationMapper,
    ): MeasurementGroupUiModelToPresentationMapper {
        return MeasurementGroupUiModelToPresentationMapper(
            scheduleItemMapper = scheduleItemMapper,
            entryMapper = entryMapper,
            fieldMapper = fieldMapper,
            patientMapper = patientMapper
        )
    }

    @Provides
    fun providesMeasurementGroupFieldUiToPresentationMapper(
        numericFieldMapper: NumericFieldUiModelToPresentationMapper
    ) = MeasurementGroupFieldUiToPresentationMapper(numericFieldMapper)

}
