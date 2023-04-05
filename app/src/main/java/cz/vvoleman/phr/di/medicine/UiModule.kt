package cz.vvoleman.phr.di.medicine

import cz.vvoleman.phr.base.presentation.navigation.NavManager
import cz.vvoleman.phr.base.ui.mapper.ViewStateBinder
import cz.vvoleman.phr.feature_medicine.databinding.FragmentListMedicineBinding
import cz.vvoleman.phr.feature_medicine.presentation.list.model.ListMedicineViewState
import cz.vvoleman.phr.feature_medicine.ui.list.mapper.ListMedicineDestinationUiMapper
import cz.vvoleman.phr.feature_medicine.ui.list.view.ListMedicineBinder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class UiModule {

    @Provides
    fun providesListMedicineBinder(): ViewStateBinder<ListMedicineViewState, FragmentListMedicineBinding> =
        ListMedicineBinder()

    @Provides
    fun providesListMedicineDestinationUiMapper(navManager: NavManager) =
        ListMedicineDestinationUiMapper(navManager)

}