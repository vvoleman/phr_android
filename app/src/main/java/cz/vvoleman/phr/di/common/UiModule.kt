package cz.vvoleman.phr.di.common

import cz.vvoleman.phr.base.presentation.navigation.NavManager
import cz.vvoleman.phr.base.ui.mapper.ViewStateBinder
import cz.vvoleman.phr.common.presentation.model.listpatients.ListPatientsViewState
import cz.vvoleman.phr.common.ui.mapper.PatientUiModelToPresentationMapper
import cz.vvoleman.phr.common.ui.mapper.destination.ListPatientsDestinationUiMapper
import cz.vvoleman.phr.common.ui.view.listpatients.ListPatientsBinder
import cz.vvoleman.phr.common_datasource.databinding.FragmentListPatientsBinding
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class UiModule {

    @Provides
    fun providesPatientUiModelToPresentationMapper() = PatientUiModelToPresentationMapper()

    @Provides
    fun providesListPatientsDestinationUiMapper(
        navManager: NavManager
    ) = ListPatientsDestinationUiMapper(navManager)

    @Provides
    fun providesListPatientsViewStateBinder(
        mapper: PatientUiModelToPresentationMapper
    ): ViewStateBinder<ListPatientsViewState, FragmentListPatientsBinding> =
        ListPatientsBinder(mapper)

}