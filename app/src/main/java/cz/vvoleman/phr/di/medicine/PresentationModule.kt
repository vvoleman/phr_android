package cz.vvoleman.phr.di.medicine

import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.feature_medicine.domain.repository.SearchMedicineRepository
import cz.vvoleman.phr.feature_medicine.domain.repository.timeline.GetSchedulesByPatientRepository
import cz.vvoleman.phr.feature_medicine.domain.usecase.GetScheduledInTimeRangeUseCase
import cz.vvoleman.phr.feature_medicine.domain.usecase.SearchMedicineUseCase
import cz.vvoleman.phr.feature_medicine.presentation.mapper.list.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class PresentationModule {

    @Provides
    fun providesSearchMedicineUseCase(
        searchMedicineRepository: SearchMedicineRepository,
        coroutineContextProvider: CoroutineContextProvider
    ) = SearchMedicineUseCase(
        searchMedicineRepository,
        coroutineContextProvider
    )

    @Provides
    fun providesGetScheduledInTimeRangeUseCase(
        getSchedulesByPatientRepository: GetSchedulesByPatientRepository,
        coroutineContextProvider: CoroutineContextProvider
    ) = GetScheduledInTimeRangeUseCase(
        getSchedulesByPatientRepository,
        coroutineContextProvider
    )

    @Provides
    fun provideProductFormPresentationModelToDomainMapper(): ProductFormPresentationModelToDomainMapper {
        return ProductFormPresentationModelToDomainMapper()
    }

    @Provides
    fun providesPackagingPresentationModelToDomainMapper(
        productFormMapper: ProductFormPresentationModelToDomainMapper
    ): PackagingPresentationModelToDomainMapper {
        return PackagingPresentationModelToDomainMapper(
            productFormMapper
        )
    }

    @Provides
    fun providesSubstancePresentationModelToDomainMapper(): SubstancePresentationModelToDomainMapper {
        return SubstancePresentationModelToDomainMapper()
    }

    @Provides
    fun providesSubstanceAmountPresentationModelToDomainMapper(
        substanceMapper: SubstancePresentationModelToDomainMapper
    ): SubstanceAmountPresentationModelToDomainMapper {
        return SubstanceAmountPresentationModelToDomainMapper(substanceMapper)
    }

    @Provides
    fun providesMedicinePresentationModelToDomainMapper(
        packagingMapper: PackagingPresentationModelToDomainMapper,
        substanceAmountMapper: SubstanceAmountPresentationModelToDomainMapper
    ): MedicinePresentationModelToDomainMapper {
        return MedicinePresentationModelToDomainMapper(
            packagingMapper,
            substanceAmountMapper
        )
    }

}