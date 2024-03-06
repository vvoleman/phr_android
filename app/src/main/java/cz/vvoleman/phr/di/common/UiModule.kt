package cz.vvoleman.phr.di.common

import android.content.Context
import cz.vvoleman.phr.base.presentation.navigation.NavManager
import cz.vvoleman.phr.base.ui.mapper.ViewStateBinder
import cz.vvoleman.phr.common.presentation.model.healthcare.addEdit.AddEditMedicalWorkerViewState
import cz.vvoleman.phr.common.presentation.model.healthcare.detailMedicalWorker.DetailMedicalWorkerViewState
import cz.vvoleman.phr.common.presentation.model.healthcare.list.ListHealthcareViewState
import cz.vvoleman.phr.common.presentation.model.patient.addedit.AddEditViewState
import cz.vvoleman.phr.common.presentation.model.patient.listpatients.ListPatientsViewState
import cz.vvoleman.phr.common.presentation.model.problemCategory.addEdit.AddEditProblemCategoryViewState
import cz.vvoleman.phr.common.presentation.model.problemCategory.detail.DetailProblemCategoryViewState
import cz.vvoleman.phr.common.presentation.model.problemCategory.list.ListProblemCategoryViewState
import cz.vvoleman.phr.common.ui.component.nextSchedule.NextScheduleItemUiModelToPresentationMapper
import cz.vvoleman.phr.common.ui.component.nextSchedule.NextScheduleUiModelToPresentationMapper
import cz.vvoleman.phr.common.ui.factory.CopyManager
import cz.vvoleman.phr.common.ui.factory.DetailMedicalWorkerContainerFactory
import cz.vvoleman.phr.common.ui.mapper.frequencySelector.FrequencyDayUiModelToPresentationMapper
import cz.vvoleman.phr.common.ui.mapper.healthcare.AddEditMedicalServiceItemUiModelToPresentationMapper
import cz.vvoleman.phr.common.ui.mapper.healthcare.MedicalFacilityAdditionalInfoUiModelToPresentationMapper
import cz.vvoleman.phr.common.ui.mapper.healthcare.MedicalFacilityUiModelToPresentationMapper
import cz.vvoleman.phr.common.ui.mapper.healthcare.MedicalServiceUiModelToPresentationMapper
import cz.vvoleman.phr.common.ui.mapper.healthcare.MedicalServiceWithWorkersUiModelToPresentationMapper
import cz.vvoleman.phr.common.ui.mapper.healthcare.MedicalWorkerAdditionalInfoUiModelToPresentationMapper
import cz.vvoleman.phr.common.ui.mapper.healthcare.MedicalWorkerUiModelToPresentationMapper
import cz.vvoleman.phr.common.ui.mapper.healthcare.MedicalWorkerWithInfoUiModelToPresentationMapper
import cz.vvoleman.phr.common.ui.mapper.healthcare.SpecificMedicalWorkerUiModelToPresentationMapper
import cz.vvoleman.phr.common.ui.mapper.healthcare.destination.AddEditMedicalWorkerDestinationUiMapper
import cz.vvoleman.phr.common.ui.mapper.healthcare.destination.DetailMedicalWorkerDestinationUiMapper
import cz.vvoleman.phr.common.ui.mapper.healthcare.destination.ListHealthcareDestinationUiMapper
import cz.vvoleman.phr.common.ui.mapper.patient.PatientUiModelToPresentationMapper
import cz.vvoleman.phr.common.ui.mapper.patient.destination.AddEditPatientDestinationUiMapper
import cz.vvoleman.phr.common.ui.mapper.patient.destination.ListPatientsDestinationUiMapper
import cz.vvoleman.phr.common.ui.mapper.problemCategory.ColorUiModelToPresentationMapper
import cz.vvoleman.phr.common.ui.mapper.problemCategory.ProblemCategoryInfoUiModelToPresentationMapper
import cz.vvoleman.phr.common.ui.mapper.problemCategory.ProblemCategoryUiModelToColorMapper
import cz.vvoleman.phr.common.ui.mapper.problemCategory.ProblemCategoryUiModelToPresentationMapper
import cz.vvoleman.phr.common.ui.mapper.problemCategory.ProblemCategoryWithInfoUiModelToPresentationMapper
import cz.vvoleman.phr.common.ui.mapper.problemCategory.destination.AddEditProblemCategoryDestinationUiMapper
import cz.vvoleman.phr.common.ui.mapper.problemCategory.destination.DetailProblemCategoryDestinationUiMapper
import cz.vvoleman.phr.common.ui.mapper.problemCategory.destination.ListProblemCategoryDestinationUiMapper
import cz.vvoleman.phr.common.ui.view.healthcare.addEdit.AddEditMedicalWorkerBinder
import cz.vvoleman.phr.common.ui.view.healthcare.detailMedicalWorker.DetailMedicalWorkerBinder
import cz.vvoleman.phr.common.ui.view.healthcare.list.ListHealthcareBinder
import cz.vvoleman.phr.common.ui.view.patient.addedit.AddEditPatientBinder
import cz.vvoleman.phr.common.ui.view.patient.listpatients.ListPatientsBinder
import cz.vvoleman.phr.common.ui.view.problemCategory.addEdit.AddEditProblemCategoryBinder
import cz.vvoleman.phr.common.ui.view.problemCategory.detail.DetailProblemCategoryBinder
import cz.vvoleman.phr.common.ui.view.problemCategory.list.ListProblemCategoryBinder
import cz.vvoleman.phr.common_datasource.databinding.FragmentAddEditMedicalWorkerBinding
import cz.vvoleman.phr.common_datasource.databinding.FragmentAddEditPatientBinding
import cz.vvoleman.phr.common_datasource.databinding.FragmentAddEditProblemCategoryBinding
import cz.vvoleman.phr.common_datasource.databinding.FragmentDetailMedicalWorkerBinding
import cz.vvoleman.phr.common_datasource.databinding.FragmentDetailProblemCategoryBinding
import cz.vvoleman.phr.common_datasource.databinding.FragmentListHealthcareBinding
import cz.vvoleman.phr.common_datasource.databinding.FragmentListPatientsBinding
import cz.vvoleman.phr.common_datasource.databinding.FragmentListProblemCategoryBinding
import cz.vvoleman.phr.featureMedicalRecord.presentation.provider.ProblemCategoryDetailProvider
import cz.vvoleman.phr.featureMedicalRecord.ui.provider.problemCategoryDetail.MedicalRecordProblemCategoryDetailProvider
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
        workerMapper: MedicalWorkerAdditionalInfoUiModelToPresentationMapper,
        facilityMapper: MedicalFacilityAdditionalInfoUiModelToPresentationMapper
    ): ViewStateBinder<ListHealthcareViewState, FragmentListHealthcareBinding> =
        ListHealthcareBinder(workerMapper, facilityMapper)

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

    @Provides
    fun providesMedicalFacilityAdditionalInfoUiModelToPresentationMapper(
        facilityMapper: MedicalFacilityUiModelToPresentationMapper
    ) = MedicalFacilityAdditionalInfoUiModelToPresentationMapper(facilityMapper)

    @Provides
    fun providesProblemCategoryUiModelToPresentationMapper() = ProblemCategoryUiModelToPresentationMapper()

    @Provides
    fun providesListProblemCategoryDestinationUiMapper(
        @ApplicationContext context: Context,
        navManager: NavManager
    ) = ListProblemCategoryDestinationUiMapper(context, navManager)

    @Provides
    fun providesListProblemCategoryBinder(
        problemCategoryMapper: ProblemCategoryUiModelToPresentationMapper,
        withInfoMapper: ProblemCategoryWithInfoUiModelToPresentationMapper
    ): ViewStateBinder<ListProblemCategoryViewState, FragmentListProblemCategoryBinding> =
        ListProblemCategoryBinder(problemCategoryMapper, withInfoMapper)

    @Provides
    fun providesProblemCategoryInfoUiModelToPresentationMapper(
        problemCategoryMapper: ProblemCategoryUiModelToPresentationMapper
    ) = ProblemCategoryInfoUiModelToPresentationMapper(problemCategoryMapper)

    @Provides
    fun providesProblemCategoryWithInfoUiModelToPresentationMapper(
        problemCategoryMapper: ProblemCategoryUiModelToPresentationMapper,
        infoMapper: ProblemCategoryInfoUiModelToPresentationMapper
    ) = ProblemCategoryWithInfoUiModelToPresentationMapper(problemCategoryMapper, infoMapper)

    @Provides
    fun providesColorUiModelToPresentationMapper() = ColorUiModelToPresentationMapper()

    @Provides
    fun providesAddEditProblemCategoryDestinationUiMapper(
        navManager: NavManager
    ) = AddEditProblemCategoryDestinationUiMapper(navManager)

    @Provides
    fun providesAddEditProblemCategoryViewStateBinder(
        colorMapper: ColorUiModelToPresentationMapper
    ): ViewStateBinder<AddEditProblemCategoryViewState, FragmentAddEditProblemCategoryBinding> =
        AddEditProblemCategoryBinder(colorMapper)

    @Provides
    fun providesSpecificMedicalWorkerUiModelToPresentationMapper(
        medicalWorkerMapper: MedicalWorkerUiModelToPresentationMapper,
        medicalServiceMapper: MedicalServiceUiModelToPresentationMapper
    ) = SpecificMedicalWorkerUiModelToPresentationMapper(medicalWorkerMapper, medicalServiceMapper)

    @Provides
    fun providesFrequencyDayUiModelToPresentationMapper() = FrequencyDayUiModelToPresentationMapper()

    @Provides
    fun providesNextScheduleItemUiModelToPresentationMapper() = NextScheduleItemUiModelToPresentationMapper()

    @Provides
    fun providesNextScheduleUiModelToPresentationMapper(
        itemMapper: NextScheduleItemUiModelToPresentationMapper
    ) = NextScheduleUiModelToPresentationMapper(itemMapper)

    @Provides
    fun providesDetailProblemCategoryDestinationUiMapper(
        navManager: NavManager
    ) = DetailProblemCategoryDestinationUiMapper(navManager)

    @Provides
    fun providesDetailProblemCategoryBinder(
    ): ViewStateBinder<DetailProblemCategoryViewState, FragmentDetailProblemCategoryBinding> =
        DetailProblemCategoryBinder()

    @Provides
    fun providesProblemCategoryDetailProvider(
        @ApplicationContext context: Context
    ): ProblemCategoryDetailProvider =
        MedicalRecordProblemCategoryDetailProvider(context)

    @Provides
    fun providesProblemCategoryUiModelToColorMapper() = ProblemCategoryUiModelToColorMapper()

    @Provides
    fun providesDetailMedicalWorkerDestinationUiMapper(
        navManager: NavManager
    ) = DetailMedicalWorkerDestinationUiMapper(navManager)

    @Provides
    fun providesDetailMedicalWorkerViewStateBinder(
        workerMapper: SpecificMedicalWorkerUiModelToPresentationMapper,
        facilityMapper: MedicalFacilityUiModelToPresentationMapper,
        factory: DetailMedicalWorkerContainerFactory
    ): ViewStateBinder<DetailMedicalWorkerViewState, FragmentDetailMedicalWorkerBinding> =
        DetailMedicalWorkerBinder(
            workerMapper,
            facilityMapper,
            factory
        )

    @Provides
    fun providesCopyManager(
        @ApplicationContext context: Context
    ) = CopyManager(context)

    @Provides
    fun providesDetailMedicalWorkerContainerFactory(
        copyManager: CopyManager
    ) = DetailMedicalWorkerContainerFactory(copyManager)

}
