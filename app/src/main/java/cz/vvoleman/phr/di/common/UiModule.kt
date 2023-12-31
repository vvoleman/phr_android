package cz.vvoleman.phr.di.common

import android.content.Context
import cz.vvoleman.phr.base.presentation.navigation.NavManager
import cz.vvoleman.phr.base.ui.mapper.ViewStateBinder
import cz.vvoleman.phr.common.presentation.model.healthcare.addEdit.AddEditMedicalWorkerViewState
import cz.vvoleman.phr.common.presentation.model.healthcare.list.ListHealthcareViewState
import cz.vvoleman.phr.common.presentation.model.patient.addedit.AddEditViewState
import cz.vvoleman.phr.common.presentation.model.patient.listpatients.ListPatientsViewState
import cz.vvoleman.phr.common.ui.mapper.healthcare.AddEditMedicalServiceItemUiModelToPresentationMapper
import cz.vvoleman.phr.common.ui.mapper.healthcare.MedicalFacilityUiModelToPresentationMapper
import cz.vvoleman.phr.common.ui.mapper.healthcare.MedicalServiceUiModelToPresentationMapper
import cz.vvoleman.phr.common.ui.mapper.healthcare.MedicalServiceWithWorkersUiModelToPresentationMapper
import cz.vvoleman.phr.common.ui.mapper.healthcare.MedicalWorkerAdditionalInfoUiModelToPresentationMapper
import cz.vvoleman.phr.common.ui.mapper.healthcare.MedicalWorkerUiModelToPresentationMapper
import cz.vvoleman.phr.common.ui.mapper.healthcare.MedicalWorkerWithInfoUiModelToPresentationMapper
import cz.vvoleman.phr.common.ui.mapper.healthcare.destination.AddEditMedicalWorkerDestinationUiMapper
import cz.vvoleman.phr.common.ui.mapper.healthcare.destination.ListHealthcareDestinationUiMapper
import cz.vvoleman.phr.common.ui.mapper.patient.PatientUiModelToPresentationMapper
import cz.vvoleman.phr.common.ui.mapper.patient.destination.AddEditPatientDestinationUiMapper
import cz.vvoleman.phr.common.ui.mapper.patient.destination.ListPatientsDestinationUiMapper
import cz.vvoleman.phr.common.ui.view.healthcare.addEdit.AddEditMedicalWorkerBinder
import cz.vvoleman.phr.common.ui.view.healthcare.list.ListHealthcareBinder
import cz.vvoleman.phr.common.ui.view.patient.addedit.AddEditPatientBinder
import cz.vvoleman.phr.common.ui.view.patient.listpatients.ListPatientsBinder
import cz.vvoleman.phr.common_datasource.databinding.FragmentAddEditMedicalWorkerBinding
import cz.vvoleman.phr.common_datasource.databinding.FragmentAddEditPatientBinding
import cz.vvoleman.phr.common_datasource.databinding.FragmentListHealthcareBinding
import cz.vvoleman.phr.common_datasource.databinding.FragmentListPatientsBinding
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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

    @Provides
    fun providesAddEditPatientViewStateBinder(): ViewStateBinder<AddEditViewState, FragmentAddEditPatientBinding> =
        AddEditPatientBinder()

    @Provides
    fun providesAddEditPatientDestinationUiMapper(
        navManager: NavManager
    ) = AddEditPatientDestinationUiMapper(navManager)

    @Provides
    fun providesListHealthcareDestinationUiMapper(
        @ApplicationContext context: Context,
        navManager: NavManager
    ) = ListHealthcareDestinationUiMapper(context, navManager)

    @Provides
    fun providesListHealthcareBinder(
        workerMapper: MedicalWorkerAdditionalInfoUiModelToPresentationMapper
    ): ViewStateBinder<ListHealthcareViewState, FragmentListHealthcareBinding> =
        ListHealthcareBinder(workerMapper)

    @Provides
    fun providesAddEditMedicalWorkerDestinationUiMapper(
        navManager: NavManager
    ) = AddEditMedicalWorkerDestinationUiMapper(navManager)

    @Provides
    fun providesAddEditMedicalWorkerBinder(
        addEditMapper: AddEditMedicalServiceItemUiModelToPresentationMapper
    ): ViewStateBinder<AddEditMedicalWorkerViewState, FragmentAddEditMedicalWorkerBinding> =
        AddEditMedicalWorkerBinder(addEditMapper)

    @Provides
    fun providesMedicalFacilityUiModelToPresentationMapper(
        medicalServiceMapper: MedicalServiceWithWorkersUiModelToPresentationMapper
    ) = MedicalFacilityUiModelToPresentationMapper(medicalServiceMapper)

    @Provides
    fun providesMedicalServiceUiModelToPresentationMapper() = MedicalServiceUiModelToPresentationMapper()

    @Provides
    fun providesMedicalWorkerUiModelToPresentationMapper() = MedicalWorkerUiModelToPresentationMapper()

    @Provides
    fun providesMedicalWorkerWithInfoUiModelToPresentationMapper(
        workerMapper: MedicalWorkerUiModelToPresentationMapper
    ) = MedicalWorkerWithInfoUiModelToPresentationMapper(workerMapper)

    @Provides
    fun providesMedicalServiceWithWorkersUiModelToPresentationMapper(
        workerWithInfo: MedicalWorkerWithInfoUiModelToPresentationMapper,
        serviceMapper: MedicalServiceUiModelToPresentationMapper
    ) = MedicalServiceWithWorkersUiModelToPresentationMapper(serviceMapper, workerWithInfo)

    @Provides
    fun providesAddEditMedicalServiceItemUiModelToPresentationMapper(
        facilityMapper: MedicalFacilityUiModelToPresentationMapper
    ) = AddEditMedicalServiceItemUiModelToPresentationMapper(facilityMapper)

    @Provides
    fun providesMedicalWorkerAdditionalInfoUiModelToPresentationMapper(
        workerMapper: MedicalWorkerUiModelToPresentationMapper
    ) = MedicalWorkerAdditionalInfoUiModelToPresentationMapper(workerMapper)
}
