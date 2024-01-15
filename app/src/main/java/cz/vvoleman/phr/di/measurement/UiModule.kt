package cz.vvoleman.phr.di.measurement

import android.content.Context
import cz.vvoleman.featureMeasurement.databinding.FragmentAddEditMeasurementBinding
import cz.vvoleman.featureMeasurement.databinding.FragmentListMeasurementBinding
import cz.vvoleman.featureMeasurement.presentation.model.addEdit.AddEditMeasurementViewState
import cz.vvoleman.featureMeasurement.presentation.model.list.ListMeasurementViewState
import cz.vvoleman.featureMeasurement.ui.mapper.addEdit.destination.AddEditMeasurementDestinationUiMapper
import cz.vvoleman.featureMeasurement.ui.mapper.list.destination.ListMeasurementDestinationUiMapper
import cz.vvoleman.featureMeasurement.ui.view.addEdit.AddEditMeasurementBinder
import cz.vvoleman.featureMeasurement.ui.view.list.ListMeasurementBinder
import cz.vvoleman.phr.base.presentation.navigation.NavManager
import cz.vvoleman.phr.base.ui.mapper.ViewStateBinder
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

}
