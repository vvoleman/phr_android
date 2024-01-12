package cz.vvoleman.phr.di.measurement

import cz.vvoleman.featureMeasurement.databinding.FragmentListMeasurementBinding
import cz.vvoleman.featureMeasurement.presentation.model.list.ListMeasurementViewState
import cz.vvoleman.featureMeasurement.ui.mapper.list.destination.ListMeasurementDestinationUiMapper
import cz.vvoleman.featureMeasurement.ui.view.list.ListMeasurementBinder
import cz.vvoleman.phr.base.presentation.navigation.NavManager
import cz.vvoleman.phr.base.ui.mapper.ViewStateBinder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class UiModule {

    @Provides
    fun providesListMeasurementDestinationUiMapper(
        navManager: NavManager
    ) = ListMeasurementDestinationUiMapper(navManager)

    @Provides
    fun providesListMeasurementViewBinder(
    ): ViewStateBinder<ListMeasurementViewState, FragmentListMeasurementBinding> = ListMeasurementBinder()

}
