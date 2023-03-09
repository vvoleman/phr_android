package cz.vvoleman.phr.di.medical_record

import cz.vvoleman.phr.base.ui.mapper.ViewStateBinder
import cz.vvoleman.phr.feature_medicalrecord.databinding.FragmentListMedicalRecordsBinding
import cz.vvoleman.phr.feature_medicalrecord.presentation.list.model.ListMedicalRecordsViewState
import cz.vvoleman.phr.feature_medicalrecord.ui.view.binder.ButtonBinder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class UiModule {

    @Provides
    fun providesViewStateBinder(): ViewStateBinder<ListMedicalRecordsViewState, FragmentListMedicalRecordsBinding> =
        ButtonBinder()

}