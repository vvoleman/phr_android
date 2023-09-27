package cz.vvoleman.phr.di.medicine

import cz.vvoleman.phr.base.presentation.navigation.NavManager
import cz.vvoleman.phr.base.ui.mapper.ViewStateBinder
import cz.vvoleman.phr.featureMedicine.databinding.FragmentAddEditMedicineBinding
import cz.vvoleman.phr.featureMedicine.databinding.FragmentListMedicineBinding
import cz.vvoleman.phr.featureMedicine.presentation.addEdit.model.AddEditMedicineViewState
import cz.vvoleman.phr.featureMedicine.presentation.list.model.ListMedicineViewState
import cz.vvoleman.phr.featureMedicine.ui.addEdit.mapper.AddEditMedicineDestinationUiMapper
import cz.vvoleman.phr.featureMedicine.ui.addEdit.view.AddEditMedicineBinder
import cz.vvoleman.phr.featureMedicine.ui.list.mapper.ListMedicineDestinationUiMapper
import cz.vvoleman.phr.featureMedicine.ui.list.view.ListMedicineBinder
import cz.vvoleman.phr.featureMedicine.ui.mapper.addEdit.FrequencyDayUiModelToPresentationMapper
import cz.vvoleman.phr.featureMedicine.ui.mapper.addEdit.TimeUiModelToPresentationMapper
import cz.vvoleman.phr.featureMedicine.ui.mapper.list.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class UiModule {

    @Provides
    fun providesListMedicineBinder(
        medicineMapper: MedicineUiModelToPresentationMapper
    ): ViewStateBinder<ListMedicineViewState, FragmentListMedicineBinding> =
        ListMedicineBinder(medicineMapper)

    @Provides
    fun providesListMedicineDestinationUiMapper(navManager: NavManager) =
        ListMedicineDestinationUiMapper(navManager)

    @Provides
    fun providesAddEditMedicineDestinationUiMapper(navManager: NavManager) =
        AddEditMedicineDestinationUiMapper(navManager)

    @Provides
    fun providesAddEditMedicineBinder(
        medicineMapper: MedicineUiModelToPresentationMapper,
        timeMapper: TimeUiModelToPresentationMapper
    ): ViewStateBinder<AddEditMedicineViewState, FragmentAddEditMedicineBinding> =
        AddEditMedicineBinder(medicineMapper, timeMapper)

    @Provides
    fun provideProductFormUiModelToPresentationMapper(): ProductFormUiModelToPresentationMapper {
        return ProductFormUiModelToPresentationMapper()
    }

    @Provides
    fun providesPackagingUiModelToPresentationMapper(
        productFormMapper: ProductFormUiModelToPresentationMapper
    ): PackagingUiModelToPresentationMapper {
        return PackagingUiModelToPresentationMapper(
            productFormMapper
        )
    }

    @Provides
    fun providesSubstanceUiModelToPresentationMapper(): SubstanceUiModelToPresentationMapper {
        return SubstanceUiModelToPresentationMapper()
    }

    @Provides
    fun providesSubstanceAmountUiModelToPresentationMapper(
        substanceMapper: SubstanceUiModelToPresentationMapper
    ): SubstanceAmountUiModelToPresentationMapper {
        return SubstanceAmountUiModelToPresentationMapper(substanceMapper)
    }

    @Provides
    fun providesMedicineUiModelToPresentationMapper(
        packagingMapper: PackagingUiModelToPresentationMapper,
        substanceAmountMapper: SubstanceAmountUiModelToPresentationMapper
    ): MedicineUiModelToPresentationMapper {
        return MedicineUiModelToPresentationMapper(
            packagingMapper,
            substanceAmountMapper
        )
    }

    @Provides
    fun providesTimeUiModelToPresentationMapper(): TimeUiModelToPresentationMapper {
        return TimeUiModelToPresentationMapper()
    }

    @Provides
    fun providesFrequencyDayUiModelToPresentationMapper(): FrequencyDayUiModelToPresentationMapper {
        return FrequencyDayUiModelToPresentationMapper()
    }
}
