package cz.vvoleman.phr.di.medicine

import cz.vvoleman.phr.base.presentation.navigation.NavManager
import cz.vvoleman.phr.base.ui.mapper.ViewStateBinder
import cz.vvoleman.phr.feature_medicine.databinding.FragmentListMedicineBinding
import cz.vvoleman.phr.feature_medicine.presentation.list.model.ListMedicineViewState
import cz.vvoleman.phr.feature_medicine.ui.list.mapper.ListMedicineDestinationUiMapper
import cz.vvoleman.phr.feature_medicine.ui.list.view.ListMedicineBinder
import cz.vvoleman.phr.feature_medicine.ui.mapper.list.*
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


}