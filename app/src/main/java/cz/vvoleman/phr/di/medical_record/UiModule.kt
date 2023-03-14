package cz.vvoleman.phr.di.medical_record

import cz.vvoleman.phr.base.presentation.navigation.NavManager
import cz.vvoleman.phr.base.ui.mapper.DestinationUiMapper
import cz.vvoleman.phr.base.ui.mapper.ViewStateBinder
import cz.vvoleman.phr.feature_medicalrecord.databinding.FragmentAddEditMedicalRecordBinding
import cz.vvoleman.phr.feature_medicalrecord.databinding.FragmentListMedicalRecordsBinding
import cz.vvoleman.phr.feature_medicalrecord.presentation.addedit.model.AddEditViewState
import cz.vvoleman.phr.feature_medicalrecord.presentation.list.model.ListMedicalRecordsViewState
import cz.vvoleman.phr.feature_medicalrecord.ui.mapper.ListMedicalRecordsDestinationUiMapper
import cz.vvoleman.phr.feature_medicalrecord.ui.view.addedit.binder.AddEditBinder
import cz.vvoleman.phr.feature_medicalrecord.ui.view.binder.ButtonBinder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class UiModule {

    @Provides
    fun providesButtonViewStateBinder(): ViewStateBinder<ListMedicalRecordsViewState, FragmentListMedicalRecordsBinding> =
        ButtonBinder()

    @Provides
    fun providesAddEditBinder(): ViewStateBinder<AddEditViewState, FragmentAddEditMedicalRecordBinding> =
        AddEditBinder()

    @Provides
    fun providesListMedicalRecordsDestinationUiMapper(navManager: NavManager): DestinationUiMapper = ListMedicalRecordsDestinationUiMapper(navManager)

}