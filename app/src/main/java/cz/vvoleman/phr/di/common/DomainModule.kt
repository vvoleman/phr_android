package cz.vvoleman.phr.di.common

import android.content.Context
import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.base.domain.eventBus.EventBusChannel
import cz.vvoleman.phr.common.data.repository.problemCategory.ProblemCategoryRepository
import cz.vvoleman.phr.common.domain.mapper.patient.PatientDomainModelToAddEditMapper
import cz.vvoleman.phr.common.domain.model.healthcare.AdditionalInfoDomainModel
import cz.vvoleman.phr.common.domain.model.healthcare.facility.MedicalFacilityDomainModel
import cz.vvoleman.phr.common.domain.model.healthcare.worker.MedicalWorkerDomainModel
import cz.vvoleman.phr.common.domain.model.problemCategory.ProblemCategoryDomainModel
import cz.vvoleman.phr.common.domain.model.problemCategory.ProblemCategoryInfoDomainModel
import cz.vvoleman.phr.common.domain.repository.healthcare.DeleteMedicalWorkerRepository
import cz.vvoleman.phr.common.domain.repository.healthcare.GetFacilitiesByPatientRepository
import cz.vvoleman.phr.common.domain.repository.healthcare.GetFacilityByIdRepository
import cz.vvoleman.phr.common.domain.repository.healthcare.GetMedicalWorkersWithServicesRepository
import cz.vvoleman.phr.common.domain.repository.healthcare.GetSpecificMedicalWorkersRepository
import cz.vvoleman.phr.common.domain.repository.healthcare.RemoveSpecificMedicalWorkerRepository
import cz.vvoleman.phr.common.domain.repository.healthcare.SaveMedicalFacilityRepository
import cz.vvoleman.phr.common.domain.repository.healthcare.SaveMedicalWorkerRepository
import cz.vvoleman.phr.common.domain.repository.healthcare.SaveSpecificMedicalWorkerRepository
import cz.vvoleman.phr.common.domain.repository.problemCategory.DeleteProblemCategoryRepository
import cz.vvoleman.phr.common.domain.repository.problemCategory.GetDefaultProblemCategoryRepository
import cz.vvoleman.phr.common.domain.repository.problemCategory.GetProblemCategoriesRepository
import cz.vvoleman.phr.common.domain.repository.problemCategory.SaveProblemCategoryRepository
import cz.vvoleman.phr.common.domain.usecase.healthcare.DeleteMedicalWorkerUseCase
import cz.vvoleman.phr.common.domain.usecase.healthcare.GetMedicalFacilitiesUseCase
import cz.vvoleman.phr.common.domain.usecase.healthcare.GetMedicalWorkersUseCase
import cz.vvoleman.phr.common.domain.usecase.healthcare.SaveMedicalWorkerUseCase
import cz.vvoleman.phr.common.domain.usecase.problemCategory.DeleteProblemCategoryUseCase
import cz.vvoleman.phr.common.domain.usecase.problemCategory.GetProblemCategoriesUseCase
import cz.vvoleman.phr.common.domain.usecase.problemCategory.SaveProblemCategoryUseCase
import cz.vvoleman.phr.common.presentation.event.GetMedicalFacilitiesAdditionalInfoEvent
import cz.vvoleman.phr.common.presentation.event.GetMedicalWorkersAdditionalInfoEvent
import cz.vvoleman.phr.common.presentation.event.MedicalWorkerDeletedEvent
import cz.vvoleman.phr.common.presentation.event.problemCategory.DeleteProblemCategoryEvent
import cz.vvoleman.phr.common.presentation.event.problemCategory.ExportProblemCategoryEvent
import cz.vvoleman.phr.common.presentation.event.problemCategory.GetProblemCategoriesAdditionalInfoEvent
import cz.vvoleman.phr.common.presentation.eventBus.CommonEventBus
import cz.vvoleman.phr.common.presentation.eventBus.CommonListener
import cz.vvoleman.phr.common.ui.export.usecase.DocumentPage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {

    @Provides
    fun providesPatientDomainModelToAddEditMapper() = PatientDomainModelToAddEditMapper()

    @Provides
    fun providesCommonListener(
        commonEventBus: CommonEventBus,
        @ApplicationContext context: Context,
        getSpecificMedicalWorkersRepository: GetSpecificMedicalWorkersRepository,
        getFacilityByIdRepository: GetFacilityByIdRepository,
        deleteMedicalWorkerRepository: DeleteMedicalWorkerRepository,
        deleteProblemCategoryRepository: DeleteProblemCategoryRepository,
        getProblemCategoriesRepository: GetProblemCategoriesRepository
    ) = CommonListener(
        commonEventBus,
        context,
        getSpecificMedicalWorkersRepository,
        getFacilityByIdRepository,
        getProblemCategoriesRepository,
        deleteMedicalWorkerRepository,
        deleteProblemCategoryRepository
    )

    @Provides
    fun providesGetMedicalWorkersUseCase(
        eventBusChannel: EventBusChannel<
            GetMedicalWorkersAdditionalInfoEvent,
            Map<MedicalWorkerDomainModel, List<AdditionalInfoDomainModel<MedicalWorkerDomainModel>>>
            >,
        getMedicalWorkersWithServicesRepository: GetMedicalWorkersWithServicesRepository,
        coroutineContextProvider: CoroutineContextProvider
    ) = GetMedicalWorkersUseCase(
        eventBusChannel,
        getMedicalWorkersWithServicesRepository,
        coroutineContextProvider
    )

    @Provides
    fun providesSaveMedicalWorkerUseCase(
        saveMedicalFacilityRepository: SaveMedicalFacilityRepository,
        saveMedicalWorkerRepository: SaveMedicalWorkerRepository,
        getSpecificMedicalWorkersRepository: GetSpecificMedicalWorkersRepository,
        saveSpecificMedicalWorkerRepository: SaveSpecificMedicalWorkerRepository,
        removeSpecificMedicalWorkerRepository: RemoveSpecificMedicalWorkerRepository,
        coroutineContextProvider: CoroutineContextProvider
    ) = SaveMedicalWorkerUseCase(
        saveMedicalFacilityRepository,
        saveMedicalWorkerRepository,
        getSpecificMedicalWorkersRepository,
        saveSpecificMedicalWorkerRepository,
        removeSpecificMedicalWorkerRepository,
        coroutineContextProvider
    )

    @Provides
    fun providesDeleteMedicalWorkerUseCase(
        deleteMedicalWorkerRepository: DeleteMedicalWorkerRepository,
        eventBusChannel: EventBusChannel<MedicalWorkerDeletedEvent, Unit>,
        coroutineContextProvider: CoroutineContextProvider
    ) = DeleteMedicalWorkerUseCase(
        eventBusChannel,
        deleteMedicalWorkerRepository,
        coroutineContextProvider
    )

    @Provides
    fun getMedicalFacilitiesUseCase(
        eventBusChannel: EventBusChannel<
            GetMedicalFacilitiesAdditionalInfoEvent,
            Map<MedicalFacilityDomainModel, List<AdditionalInfoDomainModel<MedicalFacilityDomainModel>>>
            >,
        getFacilitiesByPatientRepository: GetFacilitiesByPatientRepository,
        coroutineContextProvider: CoroutineContextProvider
    ) = GetMedicalFacilitiesUseCase(
        eventBusChannel,
        getFacilitiesByPatientRepository,
        coroutineContextProvider
    )

    @Provides
    fun providesGetProblemCategoriesUseCase(
        eventBusChannel: EventBusChannel<
            GetProblemCategoriesAdditionalInfoEvent,
            Map<ProblemCategoryDomainModel, ProblemCategoryInfoDomainModel>
            >,
        getProblemCategoriesRepository: GetProblemCategoriesRepository,
        coroutineContextProvider: CoroutineContextProvider
    ) = GetProblemCategoriesUseCase(
        eventBusChannel,
        getProblemCategoriesRepository,
        coroutineContextProvider
    )

    @Provides
    fun providesSaveProblemCategoryUseCase(
        saveProblemCategoryRepository: SaveProblemCategoryRepository,
        coroutineContextProvider: CoroutineContextProvider
    ) = SaveProblemCategoryUseCase(
        saveProblemCategoryRepository,
        coroutineContextProvider
    )

    @Provides
    fun providesDeleteProblemCategoryUseCase(
        eventBusChannel: EventBusChannel<DeleteProblemCategoryEvent, Unit>,
        getDefaultProblemCategoryRepository: GetDefaultProblemCategoryRepository,
        deleteProblemCategoryRepository: DeleteProblemCategoryRepository,
        coroutineContextProvider: CoroutineContextProvider
    ) = DeleteProblemCategoryUseCase(
        eventBusChannel,
        getDefaultProblemCategoryRepository,
        deleteProblemCategoryRepository,
        coroutineContextProvider
    )

    @Provides
    fun providesGetDefaultProblemCategoryRepository(
        problemCategoryRepository: ProblemCategoryRepository
    ): GetDefaultProblemCategoryRepository = problemCategoryRepository

    @Provides
    fun providesExportProblemCategoryEventBusChannel(
        commonEventBus: CommonEventBus
    ): EventBusChannel<ExportProblemCategoryEvent, List<DocumentPage>> = commonEventBus.exportProblemCategoryBus
}
