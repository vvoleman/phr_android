package cz.vvoleman.phr.di.event

import cz.vvoleman.phr.base.presentation.navigation.NavManager
import cz.vvoleman.phr.base.ui.mapper.ViewStateBinder
import cz.vvoleman.phr.common.ui.mapper.healthcare.SpecificMedicalWorkerUiModelToPresentationMapper
import cz.vvoleman.phr.common.ui.mapper.patient.PatientUiModelToPresentationMapper
import cz.vvoleman.phr.featureEvent.databinding.FragmentListEventBinding
import cz.vvoleman.phr.featureEvent.presentation.model.list.ListEventViewState
import cz.vvoleman.phr.featureEvent.ui.mapper.core.EventUiModelToPresentationMapper
import cz.vvoleman.phr.featureEvent.ui.mapper.list.destination.ListEventDestinationUiMapper
import cz.vvoleman.phr.featureEvent.ui.view.list.ListEventBinder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class UiModule {

    @Provides
    fun providesListEventDestinationUiMapper(
        navManager: NavManager
    ) = ListEventDestinationUiMapper(navManager)

    @Provides
    fun providesListEventBinder(): ViewStateBinder<ListEventViewState, FragmentListEventBinding> = ListEventBinder()

    @Provides
    fun providesEventUiModelToPresentationMapper(
        patientMapper: PatientUiModelToPresentationMapper,
        workerMapper: SpecificMedicalWorkerUiModelToPresentationMapper
    ): EventUiModelToPresentationMapper {
        return EventUiModelToPresentationMapper(patientMapper, workerMapper)
    }

}
