package cz.vvoleman.phr.di.medical_record

import cz.vvoleman.phr.base.presentation.navigation.NavManager
import cz.vvoleman.phr.base.ui.mapper.DestinationUiMapper
import cz.vvoleman.phr.base.ui.mapper.ViewStateBinder
import cz.vvoleman.phr.feature_medicalrecord.databinding.FragmentAddEditMedicalRecordBinding
import cz.vvoleman.phr.feature_medicalrecord.databinding.FragmentListMedicalRecordsBinding
import cz.vvoleman.phr.feature_medicalrecord.databinding.FragmentSelectFileBinding
import cz.vvoleman.phr.feature_medicalrecord.presentation.addedit.model.AddEditViewState
import cz.vvoleman.phr.feature_medicalrecord.presentation.list.model.ListMedicalRecordsViewState
import cz.vvoleman.phr.feature_medicalrecord.presentation.select_file.model.SelectFileViewState
import cz.vvoleman.phr.feature_medicalrecord.presentation.select_file.usecase.TakePhotoPresentationUseCase
import cz.vvoleman.phr.feature_medicalrecord.ui.mapper.*
import cz.vvoleman.phr.feature_medicalrecord.ui.usecase.TakePhotoUiUseCase
import cz.vvoleman.phr.feature_medicalrecord.ui.view.addedit.binder.AddEditBinder
import cz.vvoleman.phr.feature_medicalrecord.ui.view.binder.MedicalRecordsBinder
import cz.vvoleman.phr.feature_medicalrecord.ui.view.select_file.binder.SelectFileBinder
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
    ): ViewStateBinder<ListMedicalRecordsViewState, FragmentListMedicalRecordsBinding> =
        MedicalRecordsBinder(uiMapper, radio)

    @Provides
    fun providesAddEditBinder(): ViewStateBinder<AddEditViewState, FragmentAddEditMedicalRecordBinding> =
        AddEditBinder()

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
    fun providesMedicalRecordDomainToUiMapper() = MedicalRecorDomainModelToUiMapper()

    @Provides
    fun providesGroupedItemsDomainToUiMapper(
        mapper: MedicalRecorDomainModelToUiMapper
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
}
