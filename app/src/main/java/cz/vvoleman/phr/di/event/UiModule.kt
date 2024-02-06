package cz.vvoleman.phr.di.event

import android.content.Context
import cz.vvoleman.phr.base.presentation.navigation.NavManager
import cz.vvoleman.phr.base.ui.mapper.ViewStateBinder
import cz.vvoleman.phr.common.ui.mapper.healthcare.SpecificMedicalWorkerUiModelToPresentationMapper
import cz.vvoleman.phr.common.ui.mapper.patient.PatientUiModelToPresentationMapper
import cz.vvoleman.phr.featureEvent.databinding.FragmentAddEditEventBinding
import cz.vvoleman.phr.featureEvent.databinding.FragmentListEventBinding
import cz.vvoleman.phr.featureEvent.presentation.model.addEdit.AddEditEventViewState
import cz.vvoleman.phr.featureEvent.presentation.model.list.ListEventViewState
import cz.vvoleman.phr.featureEvent.ui.mapper.addEdit.destination.AddEditEventDestinationUiMapper
import cz.vvoleman.phr.featureEvent.ui.mapper.core.EventUiModelToPresentationMapper
import cz.vvoleman.phr.featureEvent.ui.mapper.list.destination.ListEventDestinationUiMapper
import cz.vvoleman.phr.featureEvent.ui.view.addEdit.AddEditEventBinder
import cz.vvoleman.phr.featureEvent.ui.view.list.ListEventBinder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class UiModule {

    @Provides
    fun providesListEventDestinationUiMapper(
        @ApplicationContext context: Context,
        navManager: NavManager
    ) = ListEventDestinationUiMapper(context, navManager)

    @Provides
    fun providesListEventBinder(): ViewStateBinder<ListEventViewState, FragmentListEventBinding> = ListEventBinder()

    @Provides
    fun providesEventUiModelToPresentationMapper(
        patientMapper: PatientUiModelToPresentationMapper,
        workerMapper: SpecificMedicalWorkerUiModelToPresentationMapper
    ): EventUiModelToPresentationMapper {
        return EventUiModelToPresentationMapper(patientMapper, workerMapper)
    }

    @Provides
    fun providesAddEditEventBinder(): ViewStateBinder<AddEditEventViewState, FragmentAddEditEventBinding> =
        AddEditEventBinder()

    @Provides
    fun providesAddEditEventDestinationUiMapper(
        navManager: NavManager
    ) = AddEditEventDestinationUiMapper(navManager)

}
