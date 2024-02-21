package cz.vvoleman.phr.di.medicalRecord

import cz.vvoleman.phr.base.presentation.navigation.NavManager
import cz.vvoleman.phr.base.ui.mapper.DestinationUiMapper
import cz.vvoleman.phr.base.ui.mapper.ViewStateBinder
import cz.vvoleman.phr.common.ui.mapper.healthcare.SpecificMedicalWorkerUiModelToPresentationMapper
import cz.vvoleman.phr.featureMedicalRecord.databinding.FragmentAddEditMedicalRecordBinding
import cz.vvoleman.phr.featureMedicalRecord.databinding.FragmentCropImageBinding
import cz.vvoleman.phr.featureMedicalRecord.databinding.FragmentDetailMedicalRecordBinding
import cz.vvoleman.phr.featureMedicalRecord.databinding.FragmentListMedicalRecordsBinding
import cz.vvoleman.phr.featureMedicalRecord.databinding.FragmentSelectFileBinding
import cz.vvoleman.phr.featureMedicalRecord.presentation.model.addEdit.AddEditViewState
import cz.vvoleman.phr.featureMedicalRecord.presentation.model.cropImage.CropImageViewState
import cz.vvoleman.phr.featureMedicalRecord.presentation.model.detail.DetailMedicalRecordViewState
import cz.vvoleman.phr.featureMedicalRecord.presentation.model.list.ListMedicalRecordViewState
import cz.vvoleman.phr.featureMedicalRecord.presentation.model.selectFile.SelectFileViewState
import cz.vvoleman.phr.featureMedicalRecord.presentation.usecase.TakePhotoPresentationUseCase
import cz.vvoleman.phr.featureMedicalRecord.ui.mapper.*
import cz.vvoleman.phr.featureMedicalRecord.ui.mapper.destination.AddEditDestinationUiMapper
import cz.vvoleman.phr.featureMedicalRecord.ui.mapper.destination.CropImageDestinationUiMapper
import cz.vvoleman.phr.featureMedicalRecord.ui.mapper.destination.DetailMedicalRecordDestinationUiMapper
import cz.vvoleman.phr.featureMedicalRecord.ui.mapper.destination.ListMedicalRecordsDestinationUiMapper
import cz.vvoleman.phr.featureMedicalRecord.ui.mapper.destination.SelectFileDestinationUiMapper
import cz.vvoleman.phr.featureMedicalRecord.ui.usecase.TakePhotoUiUseCase
import cz.vvoleman.phr.featureMedicalRecord.ui.view.addEdit.binder.AddEditBinder
import cz.vvoleman.phr.featureMedicalRecord.ui.view.cropImage.CropImageBinder
import cz.vvoleman.phr.featureMedicalRecord.ui.view.detail.DetailMedicalRecordBinder
import cz.vvoleman.phr.featureMedicalRecord.ui.view.list.ListMedicalRecordBinder
import cz.vvoleman.phr.featureMedicalRecord.ui.view.selectFile.binder.SelectFileBinder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class UiModule {

    @Provides
    fun providesButtonViewStateBinder(
        uiMapper: GroupedItemsDomainModelToUiMapper,
        radio: GroupByDomainModelViewIdMapper
    ): ViewStateBinder<ListMedicalRecordViewState, FragmentListMedicalRecordsBinding> =
        ListMedicalRecordBinder(uiMapper, radio)

    @Provides
    fun providesAddEditBinder(
        problemCategoryMapper: ProblemCategoryUiModelToColorMapper,
        diagnoseMapper: DiagnoseUiModelToPresentationMapper,
        specificWorkerMapper: SpecificMedicalWorkerUiModelToPresentationMapper
    ): ViewStateBinder<AddEditViewState, FragmentAddEditMedicalRecordBinding> =
        AddEditBinder(
            problemCategoryMapper,
            diagnoseMapper,
            specificWorkerMapper
        )

    @Provides
    fun providesSelectFileBinder(): ViewStateBinder<SelectFileViewState, FragmentSelectFileBinding> =
        SelectFileBinder()

    @Provides
    fun providesListMedicalRecordsDestinationUiMapper(
        navManager: NavManager
    ): DestinationUiMapper = ListMedicalRecordsDestinationUiMapper(
        navManager
    )

    @Provides
    fun providesMedicalRecordDomainToUiMapper() = MedicalRecordDomainModelToUiMapper()

    @Provides
    fun providesGroupedItemsDomainToUiMapper(
        mapper: MedicalRecordDomainModelToUiMapper
    ) = GroupedItemsDomainModelToUiMapper(
        mapper
    )

    @Provides
    fun providesGroupByDomainModelViewIdMapper() = GroupByDomainModelViewIdMapper()

    @Provides
    fun providesAddEditDestinationUiMapper(navManager: NavManager) = AddEditDestinationUiMapper(navManager)

    @Provides
    fun providesSelectFileDestinationUiMapper(navManager: NavManager) = SelectFileDestinationUiMapper(navManager)

    @Provides
    fun providesTakePhotoPresentationUseCase(): TakePhotoPresentationUseCase = TakePhotoUiUseCase()

    @Provides
    fun providesProblemCategoryUiModelToColorMapper() = ProblemCategoryUiModelToColorMapper()

    @Provides
    fun providesDiagnoseUiModelToPresentationMapper() = DiagnoseUiModelToPresentationMapper()

    @Provides
    fun providesDetailMedicalRecordDestinationUiMapper(navManager: NavManager) =
        DetailMedicalRecordDestinationUiMapper(navManager)

    @Provides
    fun providesDetailMedicalRecordBinder(): ViewStateBinder<DetailMedicalRecordViewState, FragmentDetailMedicalRecordBinding> =
        DetailMedicalRecordBinder()

    @Provides
    fun providesCropImageDestinationUiMapper(navManager: NavManager) = CropImageDestinationUiMapper(navManager)

    @Provides
    fun providesCropImageBinder(): ViewStateBinder<CropImageViewState, FragmentCropImageBinding> = CropImageBinder()
}
