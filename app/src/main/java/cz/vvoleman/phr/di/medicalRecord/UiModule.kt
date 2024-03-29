package cz.vvoleman.phr.di.medicalRecord

import cz.vvoleman.phr.base.presentation.navigation.NavManager
import cz.vvoleman.phr.base.ui.mapper.DestinationUiMapper
import cz.vvoleman.phr.base.ui.mapper.ViewStateBinder
import cz.vvoleman.phr.featureMedicalRecord.databinding.FragmentAddEditMedicalRecordBinding
import cz.vvoleman.phr.featureMedicalRecord.databinding.FragmentListMedicalRecordsBinding
import cz.vvoleman.phr.featureMedicalRecord.databinding.FragmentSelectFileBinding
import cz.vvoleman.phr.featureMedicalRecord.presentation.addEdit.model.AddEditViewState
import cz.vvoleman.phr.featureMedicalRecord.presentation.list.model.ListMedicalRecordsViewState
import cz.vvoleman.phr.featureMedicalRecord.presentation.selectFile.model.SelectFileViewState
import cz.vvoleman.phr.featureMedicalRecord.presentation.selectFile.usecase.TakePhotoPresentationUseCase
import cz.vvoleman.phr.featureMedicalRecord.ui.mapper.*
import cz.vvoleman.phr.featureMedicalRecord.ui.usecase.TakePhotoUiUseCase
import cz.vvoleman.phr.featureMedicalRecord.ui.view.addEdit.binder.AddEditBinder
import cz.vvoleman.phr.featureMedicalRecord.ui.view.binder.MedicalRecordsBinder
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
